package it.cnr.rethinkwaste;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class TranslationsAdding {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String URL = "jdbc:postgresql://localhost:5432/rethinkwaste_db";
        Class.forName("org.postgresql.Driver");
        Properties info = new Properties();
        info.put("user", "postgres");
        info.put("password", "postgres");
        Statement stmt1 = null;
        Statement stmt = null;
        Statement categoryStmt = null;
        Statement answersStmt = null;
        Statement profileStmt = null;
        Statement subprofileStmt = null;
        BufferedWriter writer = new BufferedWriter(new FileWriter("/opt/tomcat/webapps/queries.txt"));

        try {

            /* PROFILE PART */

            Connection profileConn = DriverManager.getConnection(URL, info);
            String profileQuery = "SELECT * from profile_translations where language like 'EN'";
            profileStmt = profileConn.createStatement();
            ResultSet profileRs = profileStmt.executeQuery(profileQuery);

            while (profileRs.next()) {
                String text = profileRs.getString(2);
                if(text != null) {
                    text = "'" + text + "'";
                }

                Statement profileInsertStmt = profileConn.createStatement();

                String profileQueryToWrite = "INSERT INTO profile_translations VALUES (" + profileRs.getLong(1) + ", " + text + ", 'ES');";
                System.out.println(profileQueryToWrite);
                writer.write(profileQueryToWrite);
                writer.append("\n");
                profileInsertStmt.executeUpdate(profileQueryToWrite);

                String profileQueryToWrite2 = "INSERT INTO profile_translations VALUES (" + profileRs.getLong(1) + ", " + text + ", 'CA');";
                System.out.println(profileQueryToWrite2);
                writer.write(profileQueryToWrite2);
                writer.append("\n");
                profileInsertStmt.executeUpdate(profileQueryToWrite2);
            }

            /* CATEGORY PART */

            Connection categoryConn = DriverManager.getConnection(URL, info);
            String categoryQuery = "SELECT * from category_translations where language like 'EN'";
            categoryStmt = categoryConn.createStatement();
            ResultSet categoryRs = categoryStmt.executeQuery(categoryQuery);

            while (categoryRs.next()) {
                String text = categoryRs.getString(2);
                if(text != null) {
                    text = "'" + text + "'";
                }

                Statement categoryInsertStmt = categoryConn.createStatement();

                String categoryQueryToWrite = "INSERT INTO category_translations VALUES (" + categoryRs.getLong(1) + ", " + text + ", 'ES');";
                System.out.println(categoryQueryToWrite);
                writer.write(categoryQueryToWrite);
                writer.append("\n");
                categoryInsertStmt.executeUpdate(categoryQueryToWrite);

                String categoryQueryToWrite2 = "INSERT INTO category_translations VALUES (" + categoryRs.getLong(1) + ", " + text + ", 'CA');";
                System.out.println(categoryQueryToWrite2);
                writer.write(categoryQueryToWrite2);
                writer.append("\n");
                categoryInsertStmt.executeUpdate(categoryQueryToWrite2);
            }

            /* ANSWERS PART */

            Connection answersConn = DriverManager.getConnection(URL, info);
            String answersQuery = "SELECT * from answer_translations where language like 'EN'"; /* answer_id, translation, language */
            answersStmt = answersConn.createStatement();
            ResultSet answersRs = answersStmt.executeQuery(answersQuery);

            while (answersRs.next()) {
                String text = answersRs.getString(2);

                if (text.contains("'")) {
                    text = text.replaceAll("'", "''");
                }

                text = "'" + text + "'";

                Statement answersInsertStmt = answersConn.createStatement();

                String answerQueryToWrite = "INSERT INTO answer_translations VALUES (" + answersRs.getLong(1) + ", " + text + ", 'ES');";
                System.out.println(answerQueryToWrite);
                writer.write(answerQueryToWrite);
                writer.append("\n");
                answersInsertStmt.executeUpdate(answerQueryToWrite);

                String answerQueryToWrite2 = "INSERT INTO answer_translations VALUES (" + answersRs.getLong(1) + ", " + text + ", 'CA');";
                System.out.println(answerQueryToWrite2);
                writer.write(answerQueryToWrite2);
                writer.append("\n");
                answersInsertStmt.executeUpdate(answerQueryToWrite2);
            }

            /* QUESTION PART */
            Connection conn = DriverManager.getConnection(URL, info);
            String query = "SELECT * from question_translation_object where id <= 121 order by id"; // id, guideline, text
            stmt1 = conn.createStatement();
            ResultSet rs = stmt1.executeQuery(query);

            int esId = 242;
            int caId = 362;
            int questionId = 1;
            stmt = conn.createStatement();

            while (rs.next()) {
                String text = rs.getString(3);

                if (text.contains("'")) {
                    text = text.replaceAll("'", "''");
                }

                String guideline = rs.getString(2);

                if(rs.getString(2) == null) {
                    guideline = null;
                }
                else {
                    if (guideline.contains("'")) {
                        guideline = guideline.replaceAll("'", "''");
                    }
                    guideline = "'" + guideline + "'";
                }

                String queryToWrite = "INSERT INTO question_translation_object VALUES (" + esId + ", " + guideline + ", '" + text +"');";
                System.out.println(queryToWrite);
                writer.write(queryToWrite);
                writer.append("\n");
                stmt.executeUpdate(queryToWrite);

                String caQueryToWrite = "INSERT INTO question_translation_object VALUES (" + caId + ", " + guideline + ", '" + text +"');";
                System.out.println(caQueryToWrite);
                writer.write(caQueryToWrite);
                writer.append("\n");
                stmt.executeUpdate(caQueryToWrite);

                if(questionId == 46) { questionId++; } // nella tabella question non c'è l'id 46

                String queryToWrite2 = "INSERT INTO question_translations VALUES (" + questionId + "," + esId + "," + "'ES');";
                System.out.println(queryToWrite2);
                writer.write(queryToWrite2);
                writer.append("\n");
                stmt.executeUpdate(queryToWrite2);

                String caQueryToWrite2 = "INSERT INTO question_translations VALUES (" + questionId + "," + caId + "," + "'CA');";
                System.out.println(caQueryToWrite2);
                writer.write(caQueryToWrite2);
                writer.append("\n");
                stmt.executeUpdate(caQueryToWrite2);

                questionId++;
                esId++;
                caId++;
            }

            /* SUBPROFILE PART */
            Connection subprofileConn = DriverManager.getConnection(URL, info);
            String subprofileQuery = "SELECT * from subprofile_translation_object where id <= 70 order by id"; // id, evaluation, general_comment, name
            subprofileStmt = subprofileConn.createStatement();
            ResultSet subprofileRs = subprofileStmt.executeQuery(subprofileQuery);

            int esSubProfileId = 141;
            int caSubProfileId = 211;
            int subprofileId = 1;
            stmt = conn.createStatement();

            while (subprofileRs.next()) {
                String evaluation = subprofileRs.getString(2);



                if (subprofileRs.getString(2) == null) {
                    evaluation = null;
                } else {
                    if (evaluation.contains("'")) {
                        evaluation = evaluation.replaceAll("'", "''");
                    }
                    evaluation = "'" + evaluation + "'";
                }

                String general_comment = subprofileRs.getString(3);

                if (subprofileRs.getString(3) == null) {
                    general_comment = null;
                } else {
                    if(general_comment.length() != 0) {
                        if (general_comment.substring(1, general_comment.length() - 2).contains("'")) {
                            general_comment = general_comment.replaceAll("'", "''");
                        }
                    }
                }

                String name = subprofileRs.getString(4);

                if (subprofileRs.getString(4) == null) {
                    name = null;
                } else {
                    if (name.contains("'")) {
                        name = name.replaceAll("'", "''");
                    }
                    name = "'" + name + "'";
                }

                String queryToWrite = "INSERT INTO subprofile_translation_object VALUES (" + esSubProfileId + ", " + evaluation + ", '" + general_comment + "', " + name + ");";
                System.out.println(queryToWrite);
                writer.write(queryToWrite);
                writer.append("\n");
                stmt.executeUpdate(queryToWrite);

                String caQueryToWrite = "INSERT INTO subprofile_translation_object VALUES (" + caSubProfileId + ", " + evaluation + ", '" + general_comment + "', " + name + ");";
                System.out.println(caQueryToWrite);
                writer.write(caQueryToWrite);
                writer.append("\n");
                stmt.executeUpdate(caQueryToWrite);

                String queryToWrite2 = "INSERT INTO subprofile_translations VALUES (" + subprofileId + "," + esSubProfileId + "," + "'ES');";
                System.out.println(queryToWrite2);
                writer.write(queryToWrite2);
                writer.append("\n");
                stmt.executeUpdate(queryToWrite2);

                String caQueryToWrite2 = "INSERT INTO subprofile_translations VALUES (" + subprofileId + "," + caSubProfileId + "," + "'CA');";
                System.out.println(caQueryToWrite2);
                writer.write(caQueryToWrite2);
                writer.append("\n");
                stmt.executeUpdate(caQueryToWrite2);

                subprofileId++;
                esSubProfileId++;
                caSubProfileId++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }

    }
}
