package com.ecom.service.impl;

import com.ecom.config.VnPayConfig;
import com.ecom.dto.request.CreateBillRequest;
import com.ecom.entity.Bill;
import com.ecom.entity.ProductInCart;
import com.ecom.entity.User;
import com.ecom.enumuration.EBillStatus;
import com.ecom.repository.BillRepository;
import com.ecom.repository.ProductInCartRepository;
import com.ecom.service.BillService;
import com.ecom.util.IPForRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final ProductInCartRepository productInCartRepository;
    private final IPForRequest ipForRequest;

    @SneakyThrows
    @Override
    public Bill createBill(CreateBillRequest createBillRequest, User user) {
        if (user == null)
            return null;
        Bill bill = new Bill();
        double total = 0d;
        List<ProductInCart> products = new ArrayList<>();
        for (Long id : createBillRequest.getListIdProductInCart()) {
            Optional<ProductInCart> optionalProduct = productInCartRepository.findById(id);
            if (optionalProduct.isEmpty() || optionalProduct.get().getCart().getUser().getId() != user.getId()
                || optionalProduct.get().getBill() != null)
                throw new Exception("Product is not valid!");
            ProductInCart product = optionalProduct.get();
            total += product.getQuantity() * product.getProduct().getPrice();
            products.add(product);
        }
        bill.setProducts(products);
        bill.setTotalPrice(total);
        bill.setUser(user);
        bill.setStatus(EBillStatus.UNPAYMENT);
        Bill saveBill = billRepository.save(bill);
        updateProductInCart(saveBill);
        return saveBill;
    }

    private void updateProductInCart(Bill saveBill) {
        List<ProductInCart> productInCarts = saveBill.getProducts();
        for (ProductInCart product:productInCarts) {
            product.setBill(saveBill);
            productInCartRepository.save(product);
        }
    }

    @Override
    public String paymentBill(HttpServletRequest request, User user, Long idBill, String bankCode, String responseUrl) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Bill bill = billRepository.findBillById(idBill);
        if (user == null || bill == null || bill.getUser().getId() != user.getId())
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        String contentPayment = "Thanh toán tiền mua sắm cho Ecommerce";
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", VnPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", Math.round(bill.getTotalPrice()) + "");
        vnp_Params.put("vnp_CurrCode", VnPayConfig.vnp_CurrCode);
        if (!StringUtils.isBlank(bankCode))
            vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", VnPayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_OrderInfo", contentPayment);
        vnp_Params.put("vnp_Locale", VnPayConfig.vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", responseUrl);
        vnp_Params.put("vnp_IpAddr", ipForRequest.getIPFromRequest(request));
        vnp_Params.put("vnp_CreateDate", sdf.format(calendar.getTime()));
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = sdf.format(calendar.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (!StringUtils.isBlank(fieldValue)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret,
                hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }

    @Override
    public String paymentSuccess(Long idBill) {
        Bill bill = billRepository.findBillById(idBill);
        bill.setStatus(EBillStatus.PAYMENT);
        billRepository.save(bill);
        return "success";
    }
}
