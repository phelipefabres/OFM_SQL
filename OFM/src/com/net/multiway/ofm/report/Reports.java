/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.report;

import com.net.multiway.ofm.daos.DataEventDAO;
import com.net.multiway.ofm.daos.OccurrenceDAO;
import com.net.multiway.ofm.entities.Occurrence;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.persistence.Persistence;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author phelipe
 */
public class Reports {

    public void occurrenceReport() throws SQLException, JRException {
        // HashMap paramRel = new HashMap();

        try {
            OccurrenceDAO dao = new OccurrenceDAO(Persistence.createEntityManagerFactory("ofmPU"));
            JasperReport report = JasperCompileManager.compileReport("resources/occurrenceReport.jrxml");

            JRDataSource jrds = new JRBeanCollectionDataSource(dao.findOccurrenceEntities());

            JasperPrint print = JasperFillManager.fillReport("resources/occurrenceReport.jasper", null, jrds);
            JasperExportManager.exportReportToPdfFile(print, "resources/occurrenceReport.pdf");
            JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eventsReport() throws SQLException, JRException {

        try {
            DataEventDAO dao = new DataEventDAO(Persistence.createEntityManagerFactory("ofmPU"));
            JasperReport report = JasperCompileManager.compileReport("resources/eventsReport.jrxml");

            JRDataSource jrds = new JRBeanCollectionDataSource(dao.findDataEventEntities());

            JasperPrint print = JasperFillManager.fillReport("resources/eventsReport.jasper", null, jrds);
            JasperExportManager.exportReportToPdfFile(print, "resources/eventsReport.pdf");
            JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
