package com.carlospaelinck.services

import com.carlospaelinck.domain.Document
import com.carlospaelinck.domain.User
import com.carlospaelinck.repositories.DocumentRepository
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Font
import com.itextpdf.text.Phrase
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfGState
import com.itextpdf.text.pdf.PdfLayer
import com.itextpdf.text.pdf.PdfWriter
import org.springframework.stereotype.Service

import javax.inject.Inject
import javax.transaction.Transactional
import java.awt.Color

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

        def contentByte = pdfWriter.getDirectContent()
        contentByte.colorFill = BaseColor.WHITE
        contentByte.rectangle(0, 0, docRect.width, docRect.height)
        contentByte.fill()
        contentByte.saveState()

        document.shapes.forEach({ shape ->
            def shapeLayer = new PdfLayer(shape.id, pdfWriter)
            contentByte.restoreState()
            contentByte.beginLayer(shapeLayer)

            if (shape.fill != null && shape.stroke != null) {
                def colorFill = Color.decode(shape.fill)
                def colorStroke = Color.decode(shape.stroke)
                def gState = new PdfGState()
                contentByte.setRGBColorFill(colorFill.red, colorFill.green, colorFill.blue)
                contentByte.setRGBColorStroke(colorStroke.red, colorStroke.green, colorStroke.blue)
                contentByte.lineWidth = shape.strokeWidth
                gState.setFillOpacity(shape.fillOpacity)
                gState.setStrokeOpacity(shape.strokeOpacity)
                contentByte.setGState(gState)
            }

            def width = shape.width * DPI
            def height = shape.height * DPI
            def x1 = shape.x * DPI
            def y1 = docRect.height - ((shape.y * DPI) + height)

            def drawShape = {
                if (shape.strokeWidth > 0) { contentByte.fillStroke() }
                else { contentByte.fill() }
            }

            switch (shape.type) {
                case 'ellipse':
                    def x2 = x1 + width
                    def y2 = y1 + height
                    contentByte.ellipse(x1, y1, x2, y2)
                    drawShape()
                    break

                case 'rect':
                    def r = shape.r ?: 0f
                    contentByte.roundRectangle(x1, y1, width, height, r)
                    drawShape()
                    break

                case 'text':
                    def x2 = x1 + width
                    def y2 = y1 + height
                    def color = Color.decode(shape.color)
                    def style = Font.NORMAL
                    def baseColor = new BaseColor(color.red, color.green, color.blue)
                    def font = new Font(Font.FontFamily.HELVETICA, shape.fontSize, style, baseColor)
                    def columnText = new ColumnText(contentByte)
                    def phrase = new Phrase(shape.text, font)
                    def leading = 1.1f

                    def alignment = 0

                    switch (shape.textAlign) {
                        case 'left':
                            alignment = 0
                            break
                        case 'center':
                            alignment = 1
                            break
                        case 'right':
                            alignment = 2
                            break
                    }

                    columnText.setSimpleColumn(
                            phrase,
                            (float)x1,
                            (float)y1,
                            (float)x2,
                            (float)y2,
                            font.getCalculatedLeading(leading),
                            alignment)

                    columnText.go()

                    break
            }

            contentByte.endLayer()
            contentByte.saveState()
        })

        contentByte.restoreState()
        pdfDocument.close()

        return file
    }
}
