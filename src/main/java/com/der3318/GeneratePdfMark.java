package com.der3318;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneratePdfMark {

    public static void main(String[] args) {

        /** check command parameters */
        if (args.length != 2 || args[0].equals("--help") || args[0].equals("-h")) {
            Console.Warn.println("[Usage] java -jar auto-pdf-mark.jar INPUT.pdf OUTPUT.pdf");
            return;
        }

        /** try to access pdf file */
        String pdfPath = args[0];
        PDDocument document = null;
        try {
            document = PDDocument.load(new File(pdfPath));
        } catch (IOException e) {
            String errMsg = String.format("[Error] failed to access %s", pdfPath);
            Console.Err.println(errMsg);
            return;
        }

        /** get class and check if it is encrypted */
        document.getClass();
        if (document.isEncrypted()) {
            String errMsg = String.format("[Error] failed to decrypted %s", pdfPath);
            Console.Err.println(errMsg);
            return;
        }

        /** get number of pages in the pdf */
        int numberOfPages = document.getNumberOfPages();
        Console.Info.println(String.format("[Info] detected %d pages", numberOfPages));

        /** attach outline instance and a list to save the marks */
        PDDocumentOutline outline = new PDDocumentOutline();
        document.getDocumentCatalog().setDocumentOutline(outline);
        List<String> marks = new ArrayList<>();

        /** read the pages */
        for (int pageIdx = 0; pageIdx < numberOfPages; pageIdx++) {

            /** page info */
            int pageNumber = pageIdx + 1;
            PDPage page = document.getPage(pageIdx);

            /** setup stripper and annotation container */
            PDFTextStripperByArea stripper = null;
            List<PDAnnotation> annotations = new ArrayList<>();
            try {
                stripper = new PDFTextStripperByArea();
            } catch (IOException e) {
                String errMsg = String.format("[Error] failed to setup Stripper in page No. %d", pageNumber);
                Console.Err.println(errMsg);
                return;
            }
            try {
                annotations = page.getAnnotations();
            } catch (IOException e) {
                String warnMsg = String.format("[Warn] failed to get Annotations in page No. %d", pageNumber);
                Console.Warn.println(warnMsg);
            }

            /** read annotations */
            for (PDAnnotation annotation : annotations) {

                /** check annotation type */
                if (!(annotation instanceof PDAnnotationLink)) {
                    continue;
                }

                /** cast the type and get the rectangle */
                PDAnnotationLink link = (PDAnnotationLink) annotation;
                PDRectangle rect = link.getRectangle();

                /** retrieve the parameters of the rectangle */
                float x = rect.getLowerLeftX(), y = rect.getUpperRightY(), width = rect.getWidth(), height = rect.getHeight();
                if (page.getRotation() == 0) {
                    PDRectangle pageSize = page.getMediaBox();
                    y = pageSize.getHeight() - y;
                }

                /** create Rectangle2D instance */
                Rectangle2D.Float awtRect = new Rectangle2D.Float(x, y, width, height);

                /** create temporary region and extract */
                stripper.addRegion("Page No." + pageNumber, awtRect);
                try {
                    stripper.extractRegions(page);
                } catch (IOException e) {
                    String warnMsg = String.format("[Warn] failed to extract regions in page No. %d", pageNumber);
                    Console.Warn.println(warnMsg);
                }

                /** detect the text in the region */
                String text = stripper.getTextForRegion("Page No." + pageNumber).trim();

                /** check if the mark is duplicated */
                if (marks.contains(text)) {
                    continue;
                }

                /** try to access the destination of the link and create bookmark */
                try {
                    PDNamedDestination destination = (PDNamedDestination) link.getDestination();
                    PDOutlineItem bookmark = new PDOutlineItem();
                    bookmark.setDestination(destination);
                    bookmark.setTitle(text);
                    outline.addLast(bookmark);
                    marks.add(text);
                } catch (Exception e) {
                    String warnMsg = String.format("[Warn] failed to parse destination of \"%s\" in page No. %d", text, pageNumber);
                    Console.Warn.println(warnMsg);
                }
            }
        }

        /** activate the nodes and print information message */
        outline.openNode();
        Console.Info.println(String.format("[Info] found %d potential marks", marks.size()));

        /** output new pdf */
        String outputPath = args[1];
        try {
            document.save(new File(outputPath));
            Console.Info.println(String.format("[Info] save the marked pdf as %s", outputPath));
        } catch (IOException e) {
            String errMsg = String.format("[Error] failed to create, truncate or override %s", outputPath);
            Console.Err.println(errMsg);
        }

    }

}
