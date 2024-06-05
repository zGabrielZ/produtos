package br.com.gabrielferreira.notificacao.domain.service.impl;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.model.enums.NotificacaoStatusEnum;
import br.com.gabrielferreira.notificacao.domain.service.EmailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Log4j2
@Service
public class GoogleEmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    private TemplateEngine templateEngine;

    private String emailRemetente;

    public GoogleEmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine, @Value("${spring.mail.username}") String emailRemetente) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.emailRemetente = emailRemetente;
    }

    @Override
    public Notificacao enviarEmail(Notificacao notificacao) {
        log.debug("enviarEmail notificação : {}", notificacao);

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

            Context context = new Context();
            context.setVariables(notificacao.getDados());
            String conteudoHtml = templateEngine.process(notificacao.getEmailTemplate().getTemplateEmail(), context);

            message.setFrom(new InternetAddress(emailRemetente, notificacao.getNomeRemetente()));
            message.setTo(notificacao.getDestinatarios());
            message.setSubject(notificacao.getTitulo());
            message.setText(conteudoHtml, true);

            javaMailSender.send(mimeMessage);

            notificacao.setStatus(NotificacaoStatusEnum.ENVIADO);

            log.info("E-mail enviado com sucesso");
        } catch (Exception e){
            log.error("Erro ao enviar e-mail {}", e.getMessage());
            notificacao.setStatus(NotificacaoStatusEnum.ERRO);
        }

        return notificacao;
    }
}
