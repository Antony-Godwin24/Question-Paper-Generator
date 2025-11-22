package com.example.qpg.service;

import com.example.qpg.model.Question;
import com.example.qpg.model.QuestionPaper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generatePdf(QuestionPaper paper) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph(paper.getTitle(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph subject = new Paragraph("Subject: " + paper.getSubject(), subTitleFont);
            subject.setAlignment(Element.ALIGN_CENTER);
            document.add(subject);

            document.add(Chunk.NEWLINE);

            Font questionFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            int questionNumber = 1;
            for (Question q : paper.getQuestions()) {
                Paragraph questionPara = new Paragraph(questionNumber + ". " + q.getContent(), questionFont);
                questionPara.setSpacingAfter(10);
                document.add(questionPara);
                questionNumber++;
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
