package com.emu.apps.cv;

import com.emu.apps.cv.dto.Competence;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportDocxTest {

    @Test
    public void cvTest() throws IOException, XDocReportException {

        InputStream in = ReportDocxTest.class.getResourceAsStream("trame_cv.docx");
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);


        IContext context = report.createContext();

        context.put("comments", "tototo");
        context.put("prenom", "Eric");
        context.put("nom", "MULLER");
        context.put("titre", "Ingénieur d’études Java/JEE");
        context.put("experience", "18 ans d’expérience");
        context.put("portable", "06.10.95.40.22");
        context.put("diplome", "DUT INFORMATIQUE-1998");
        // summary
        context.put("competences_summary", "18 ans d’expérience en réalisation, maintenance et intégration d’applications Java j2ee/Oracle (applications de gestion, Internet)");
        //  metiers
        List<Competence> metiers = Stream.of("Développement Java EE", "Tests, recettes", "Encadrement technique")
                .map(s -> new Competence(s)).collect(Collectors.toList());
        context.put("metiers", metiers);

        List<Competence> fonctionnelles = Stream.of("Système d’aide d’exploitation", "Pharmacologie",
                "Equipementier", "Assurances", "Retraite")
                .map(s -> new Competence(s)).collect(Collectors.toList());
        context.put("fonctionnelles", fonctionnelles);

        List<Competence> langues = Stream.of("Anglais écrit, lu")
                .map(s -> new Competence(s)).collect(Collectors.toList());
        context.put("langues", langues);


        List<Competence> environnements = Stream.of("Java : Weblogic, TOMEE, EJB, JSP, Swing",
                "Web : HTML, JavaScript, angularjs",
                "API : Webservices (Soap/XML, Rest/JSON)",
                "SGBD: Oracle, PostgreSQL, Mysql",
                "Outils: Maven, CVS, SVN, Bugzilla, Mantis",
                "Systèmes: Linux, Unix, Windows").map(s -> new Competence(s)).collect(Collectors.toList());
        context.put("environnements", environnements);


        OutputStream out = new FileOutputStream(new File("cv_out.docx"));
        report.process(context, out);

        Assert.assertNotNull(out);

    }

}
