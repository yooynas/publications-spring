package com.carlospaelinck.services

import com.carlospaelinck.domain.Document
import com.carlospaelinck.domain.User
import com.carlospaelinck.repositories.DocumentRepository
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfWriter
import org.springframework.stereotype.Service

import javax.inject.Inject
import javax.transaction.Transactional

@Service
@Transactional
class DocumentServiceImpl implements DocumentService {
    final Float DPI = 72

    @Inject
    DocumentRepository documentRepository

    @Override
    List<Document> findAllByUser(User user) {
        documentRepository.findAllByUser(user)
    }

    @Override
    Document create(Document document) {
        return documentRepository.save(document)
    }

    @Override
    Document update(Document document) {
        return documentRepository.save(document)
    }

    @Override
    File pdf(String documentId) {
        def document = documentRepository.findOne(documentId)
        def file = File.createTempFile(document.id, '.pdf')
        def outputStream = new FileOutputStream(file)
        def docRect = new Rectangle((float)(document.width * DPI), (float)(document.height * DPI))

        def pdfDocument = new com.itextpdf.text.Document(docRect)
        def pdfWriter = PdfWriter.getInstance(pdfDocument, outputStream)

        pdfDocument.setMargins(0, 0, 0, 0)
        pdfDocument.open()

        def backgroundContent = pdfWriter.getDirectContent()
        backgroundContent.colorFill = BaseColor.WHITE
        backgroundContent.rectangle(0, 0, docRect.width, docRect.height)
        backgroundContent.fill()

        pdfDocument.close()

        return file
    }
}
