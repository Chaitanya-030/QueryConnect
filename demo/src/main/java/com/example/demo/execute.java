package com.example.demo;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.engine.http.QueryExceptionHTTP;

public class execute {
    public static String executedbpedia(String sparqlString) {
        StringBuilder result=new StringBuilder();
        String queryString =
                "SELECT * WHERE { " +
                        "    SERVICE <http://dbpedia.org/sparql?timeout=10> { " +
                        sparqlString +
                        "    }" +
                        "}";
        Query queryToExecute = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(queryToExecute, ModelFactory.createDefaultModel())) {
            org.apache.jena.query.ResultSet rs = qexec.execSelect();
            result.append(ResultSetFormatter.asText(rs));

            ResultSetFormatter.out(System.out, rs, queryToExecute);
        } catch (QueryExceptionHTTP e) {
            System.err.println("QueryExceptionHTTP: " + e.getMessage());
        }
        String ret=result.toString();
        System.out.println(ret);
        return ret;
    }
}
