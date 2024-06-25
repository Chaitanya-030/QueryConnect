package com.example.demo;

import java.util.HashMap;
import java.util.List;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.algebra.OpAsQuery;
import org.apache.jena.sparql.algebra.Transform;
import org.apache.jena.sparql.algebra.TransformCopy;
import org.apache.jena.sparql.algebra.Transformer;
import org.apache.jena.sparql.algebra.op.OpBGP;
import org.apache.jena.sparql.core.BasicPattern;

public class App {
    static HashMap<String, String> mp = new HashMap<>();

    public static String manipulate(String kqlQuery)   {
    	mp.put("city", "http://dbpedia.org/ontology/city"); //
    	mp.put("Gandhinagar", "http://dbpedia.org/resource/Gandhinagar"); //
    	mp.put("type", "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"); //
    	mp.put("University", "http://dbpedia.org/ontology/University"); //
    	mp.put("Actor", "http://dbpedia.org/ontology/Actor"); //
    	mp.put("birthPlace", "http://dbpedia.org/ontology/birthPlace"); //
    	mp.put("ProgrammingLanguage", "http://dbpedia.org/ontology/ProgrammingLanguage"); //
    	mp.put("developer", "http://dbpedia.org/ontology/developer"); //
    	mp.put("India", "http://dbpedia.org/resource/India"); //
    	mp.put("starring", "http://dbpedia.org/ontology/starring"); //
    	mp.put("artist", "http://dbpedia.org/ontology/artist");
    	mp.put("Leonardo_DiCaprio", "http://dbpedia.org/resource/Leonardo_DiCaprio"); //
    	mp.put("Film", "http://dbpedia.org/ontology/Film"); //
    	mp.put("Album", "http://dbpedia.org/ontology/Album"); //
    	mp.put("Microsoft", "http://dbpedia.org/resource/Microsoft"); //
        mp.put("The_Beatles", "http://dbpedia.org/resource/The_Beatles"); //
        mp.put("Charles_Dickens", "http://dbpedia.org/resource/Charles_Dickens"); //
        mp.put("VideoGame", "http://dbpedia.org/ontology/VideoGame"); //
        mp.put("Book", "http://dbpedia.org/ontology/Book"); //
        mp.put("author", "http://dbpedia.org/ontology/author"); //
        mp.put("country", "http://dbpedia.org/ontology/country"); //
        mp.put("River", "http://dbpedia.org/ontology/River"); //
        mp.put("nationality", "http://dbpedia.org/ontology/nationality"); //
        mp.put("currency", "http://dbpedia.org/ontology/currency");
        mp.put("Nintendo", "http://dbpedia.org/resource/Nintendo"); //
        mp.put("Japan", "http://dbpedia.org/resource/Japan"); 
    	

        // Convert the KQL string into the SPARQL compatible string
        String sparqlQuery ="PREFIX kql: <http://kql#> " + preprocessKQLQuery(kqlQuery);

        // Create a query object from the SPARQL query string
        Query query = QueryFactory.create(sparqlQuery);

        // Convert the query to an algebraic expression
        Op op = Algebra.compile(query);

        // Print the algebraic expression
        // System.out.println("Algebraic Expression:");
        // System.out.println(op);

        // Traverse the algebraic expression using OpWalker

        // OpVisitor visitor = new CustomOpVisitor();
        // OpWalker.walk(op, visitor);
        Transform transform = new ReplacePrefixTransform();
        Op transformedOp = Transformer.transformSkipService(transform, op);

        // Print the transformed algebraic expression
        System.out.println("Transformed Algebraic Expression:");
        System.out.println(transformedOp);
        
        // Convert algebraic expression into query object
        Query tquery = OpAsQuery.asQuery(transformedOp);

        // convert query object into string
        String transformedString=tquery.toString();
        
        System.out.println(transformedString);
        return transformedString;    
    }

    private static String preprocessKQLQuery(String kqlQuery) {
        // Replace keywords with URIs or valid SPARQL predicates
        kqlQuery = kqlQuery.replace(" city ", " kql:city "); //
        kqlQuery = kqlQuery.replace(" type ", " kql:type "); //
        kqlQuery = kqlQuery.replace(" University ", " kql:University "); //
        kqlQuery = kqlQuery.replace(" Actor ", " kql:Actor "); //
        kqlQuery = kqlQuery.replace(" Gandhinagar ", " kql:Gandhinagar "); //
        kqlQuery = kqlQuery.replace(" birthPlace ", " kql:birthPlace "); //
        kqlQuery = kqlQuery.replace(" ProgrammingLanguage ", " kql:ProgrammingLanguage "); //
        kqlQuery = kqlQuery.replace(" developer ", " kql:developer "); //
        kqlQuery = kqlQuery.replace(" India ", " kql:India "); // 
        kqlQuery = kqlQuery.replace(" starring ", " kql:starring "); //
        kqlQuery = kqlQuery.replace(" Leonardo_DiCaprio ", " kql:Leonardo_DiCaprio "); //
        kqlQuery = kqlQuery.replace(" artist ", " kql:artist "); //
        kqlQuery = kqlQuery.replace(" Album ", " kql:Album "); //
        kqlQuery = kqlQuery.replace(" Film ", " kql:Film "); //
        kqlQuery = kqlQuery.replace(" Microsoft ", " kql:Microsoft "); //
        kqlQuery = kqlQuery.replace(" The_Beatles ", " kql:The_Beatles "); //
        kqlQuery = kqlQuery.replace(" VideoGame ", " kql:VideoGame "); //
        kqlQuery = kqlQuery.replace(" Nintendo ", " kql:Nintendo "); //
        kqlQuery = kqlQuery.replace(" River ", " kql:River "); //
        kqlQuery = kqlQuery.replace(" country ", " kql:country "); //
        kqlQuery = kqlQuery.replace(" Charles_Dickens ", " kql:Charles_Dickens "); //
        kqlQuery = kqlQuery.replace(" Book ", " kql:Book "); //
        kqlQuery = kqlQuery.replace(" author ", " kql:author "); //
        kqlQuery = kqlQuery.replace(" nationality ", " kql:nationality ");
        kqlQuery = kqlQuery.replace(" Japan ", " kql:Japan ");
        kqlQuery = kqlQuery.replace(" currency ", " kql:currency ");

        return kqlQuery;
    }

    public static Triple convertToDBpediaTriple(Node subjectNode, String predicateURI, String objectURI) {

        String predicateName = predicateURI.substring(predicateURI.lastIndexOf('#') + 1);
        System.out.println(objectURI);
    
        String dbpediapredicateURI = mp.get(predicateName);
        Node predicateNode = NodeFactory.createURI(dbpediapredicateURI);
    
        Node objectNode;
        if (objectURI.contains("?")) {
            objectNode = NodeFactory.createVariable(objectURI.substring(1));
        } else {
            String objectName = objectURI.substring(objectURI.lastIndexOf('#') + 1);
            String dbpediaobjectURI = mp.get(objectName);
            objectNode = NodeFactory.createURI(dbpediaobjectURI);
        }
    
        return Triple.create(subjectNode, predicateNode, objectNode);
    }

    static class ReplacePrefixTransform extends TransformCopy {

        @Override
        public Op transform(OpBGP opBGP) {
            BasicPattern pattern = opBGP.getPattern();
            BasicPattern newPattern = new BasicPattern();
            List<Triple> triples = pattern.getList();
            for (int i = 0; i < triples.size(); ++i) {
                Triple tr = triples.get(i);

                Node subject = tr.getSubject();
                Node predicate = tr.getPredicate();
                Node object = tr.getObject();
                
                String objectURI;
                if (object.isURI()) {
                    objectURI = object.getURI();
                } else {
                    // If the object is a variable, use its name with a "?" prefix
                    objectURI = "?" + object.getName();
                }

                Triple newtr = convertToDBpediaTriple(subject, predicate.getURI(), objectURI);
                newPattern.add(newtr);
            }

            // Create a new OpBGP with the modified pattern
            OpBGP newOpBGP = new OpBGP(newPattern);

            return newOpBGP;
        }
    }
}