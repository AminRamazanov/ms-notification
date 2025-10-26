package com.example.msnotifiction.properties;

import com.example.msnotifiction.enums.CheckStatus;
import com.example.msnotifiction.model.request.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Slf4j
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagePattern {
    private String subject;
    private String text;

    @Value("${activateLink}")
    private String link;

    public MessagePattern orderCompletedMessage(OrderCompletedEvent orderCompletedEvent) {
        subject = "Bizi seçdiyiniz üçün təşəkkür edirik!";
        text = "Salam,\n\n" +
                "Sifarişiniz №" + orderCompletedEvent.getId() + " təhvil verildi.\n" +
                "Zəhmət olmasa, sifarişi götürmək üçün mağazamıza yaxınlaşın.\n\n" +
                "Təşəkkür edirik ki, bizi seçdiniz!\n\nHörmətlə,\nFloraFlow";
        return MessagePattern.builder()
                .subject(subject)
                .text(text)
                .build();
    }

    public MessagePattern notifyOrder(OrderReadyEvent orderReadyEvent) {
        subject = "Sifarişiniz Hazırdır!";
        text = "Salam,\n\n" +
                "Sifarişiniz №" + orderReadyEvent.getId() + " hazırdır.\n" +
                "Zəhmət olmasa, sifarişi götürmək üçün mağazamıza yaxınlaşın.\n\n" +
                "Təşəkkür edirik ki, bizi seçdiniz!\n\nHörmətlə,\nFloraFlow";
        return MessagePattern.builder()
                .subject(subject)
                .text(text)
                .build();
    }

    public MessagePattern orderResultEvent(OrderResultNotificationEvent orderResultNotificationEvent) {
        String htmlContent = "";

        if (CheckStatus.SUCCESS.name().equals(orderResultNotificationEvent.getStatus())) {
            subject = "Sifarişiniz üçün təşəkkür edirik!";
            StringBuilder tableBuilder = new StringBuilder();
            tableBuilder.append("<html><body>");
            tableBuilder.append("<h3>Sifarişiniz üçün təşəkkür edirik!</h3>");
            tableBuilder.append("<p>Sifariş № ").append(orderResultNotificationEvent.getId()).append("</p>");
            tableBuilder.append("<table border='1' cellpadding='6' cellspacing='0'" +
                    " style='border-collapse: collapse; width: 100%;'>");
            tableBuilder.append("<tr style='background-color: #f2f2f2;'>")
                    .append("<th>Məhsul</th><th>Qiymət</th><th>Miqdar</th><th>Məbləğ</th></tr>");

            if (orderResultNotificationEvent.getItems() != null) {
                for (OrderItemEvent item : orderResultNotificationEvent.getItems()) {
                    BigDecimal price = item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO;
                    int quantity = item.getQuantity() != null ? item.getQuantity() : 0;
                    BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));

                    tableBuilder.append("<tr>")
                            .append("<td>").append(item.getCatalogItemName()).append(" kod:")
                            .append(item.getCatalogItemId()).append("</td>")
                            .append("<td>").append(price).append(" AZN</td>")
                            .append("<td>").append(quantity).append("</td>")
                            .append("<td>").append(total).append(" AZN</td>")
                            .append("</tr>");
                }
            }

            tableBuilder.append("</table><br/>")
                    .append("<p><b>ÜMUMİ MƏBLƏĞ: ").append(orderResultNotificationEvent.getTotalPrice())
                    .append(" AZN</b></p>")
                    .append("<br/><a href='http://localhost:8080/v1/order/")
                    .append(orderResultNotificationEvent.getId())
                    .append("/status' style='display:inline-block;padding:10px 15px;background:#28a745;" +
                            "color:white;text-decoration:none;border-radius:5px;'>Sifarişin statusunu yoxlamaq</a>")
                    .append("</body></html>");

            htmlContent = tableBuilder.toString();
            log.info("Email sent to {} for order {}",
                    orderResultNotificationEvent.getEmail(),
                    orderResultNotificationEvent.getId());

        } else if (CheckStatus.FAILED.name().equals(orderResultNotificationEvent.getStatus())) {
            subject = "Sifarişiniz uğursuz oldu";
            htmlContent = "<html><body>"
                    + "<h3>Sifarişinizi tamamlamaq olmadı</h3>"
                    + "<p>Sifariş № " + orderResultNotificationEvent.getId() + "</p>"
                    + "<p><b>Səbəb:</b> " + orderResultNotificationEvent.getMessage() + "</p>"
                    + "</body></html>";
            log.info("Failed email sent to {} for order {}",
                    orderResultNotificationEvent.getEmail(),
                    orderResultNotificationEvent.getId());
        }

        return MessagePattern.builder()
                .subject(subject)
                .text(htmlContent)
                .build();
    }

    public MessagePattern userActivate(UserActivate userActivate){

        subject= "Hesab Aktivləşdirilməsi";

        String activationUrl = link + userActivate.getKey();

        text = "Salam " + userActivate.getUsername() + ",\n\n" +
                "Bizim platformamıza qeydiyyatdan keçdiyiniz üçün sizə təşəkkür edirik!\n\n" +
                "Hesabınızı aktivləşdirmək üçün aşağıdakı linkə klikləyin:\n" +
                activationUrl + "\n\n" +
                "Əgər siz bu qeydiyyatı etməmisinizsə, bu e-poçtu nəzərə almayın.\n\n" +
                "Hörmətlə,\nFloraFlow Komandası";

        return MessagePattern.builder()
                .subject(subject)
                .text(text)
                .build();
    }

    public MessagePattern otpRecoveryMessage(RecoveryPasswordEvent recoveryPasswordEvent){

        subject= "Şifrə bərpası üçün OTP";
        text = "Salam!\n\n" +
                "Şifrənizi bərpa etmək üçün bir dəfəlik kodunuz (OTP): " + recoveryPasswordEvent.getOtp() + "\n" +
                "Kod yalnız 10 dəqiqə üçün etibarlıdır.\n\n" +
                "Əgər siz şifrə bərpası istəmədinizsə, bu mesajı nəzərə almayın.\n\n" +
                "Hörmətlə,\nFloraFlow";

        return MessagePattern.builder()
                .subject(subject)
                .text(text)
                .build();
    }
}
