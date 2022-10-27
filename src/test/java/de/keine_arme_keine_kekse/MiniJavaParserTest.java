package de.keine_arme_keine_kekse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.keine_arme_keine_kekse.parser.MiniJavaParser;
import de.keine_arme_keine_kekse.parser.ParseException;
import de.keine_arme_keine_kekse.syntaxtree.And;
import de.keine_arme_keine_kekse.syntaxtree.Exp;
import de.keine_arme_keine_kekse.syntaxtree.False;
import de.keine_arme_keine_kekse.syntaxtree.LessThan;
import de.keine_arme_keine_kekse.syntaxtree.Minus;
import de.keine_arme_keine_kekse.syntaxtree.Not;
import de.keine_arme_keine_kekse.syntaxtree.Plus;
import de.keine_arme_keine_kekse.syntaxtree.This;
import de.keine_arme_keine_kekse.syntaxtree.Times;
import de.keine_arme_keine_kekse.syntaxtree.True;
import de.keine_arme_keine_kekse.syntaxtree.VarDecl;

public class MiniJavaParserTest {

    @Test()
    @Disabled
    public void parsesVarDeclaration() throws ParseException {
        MiniJavaParser parser = parserFor("int number = 0;");
        VarDecl decl = parser.VarDecl();
        assertEquals("number", decl.id.getName());
        assertEquals("int", decl.type.toString());
    }

    @Test()
    public void parsesFalse() throws ParseException {
        MiniJavaParser parser = parserFor("false");
        Exp result = parser.UnaryExpression();
        assertEquals(False.class, result.getClass());
    }

    @Test()
    public void parsesTrue() throws ParseException {
        MiniJavaParser parser = parserFor("true");
        Exp result = parser.UnaryExpression();
        assertEquals(True.class, result.getClass());
    }

    @Test()
    public void parsesThis() throws ParseException {
        MiniJavaParser parser = parserFor("this");
        Exp result = parser.UnaryExpression();
        assertEquals(This.class, result.getClass());
    }

    @Test()
    public void parsesNot() throws ParseException {
        MiniJavaParser parser = parserFor("!true");
        Exp result = parser.UnaryExpression();
        assertEquals(Not.class, result.getClass());

        Not not = (Not) result;
        assertEquals(True.class, not.exp.getClass());
    }

    @Test()
    public void parsesAndExpression() throws ParseException {
        MiniJavaParser parser = parserFor("true && false");
        Exp result = parser.Expression();
        assertEquals(And.class, result.getClass());
    }

    @Test()
    public void parsesLessThan() throws ParseException {
        MiniJavaParser parser = parserFor("0 < 1");
        Exp result = parser.Expression();
        assertEquals(LessThan.class, result.getClass());
    }

    @Test()
    public void parsesPlus() throws ParseException {
        MiniJavaParser parser = parserFor("0 + 1");
        Exp result = parser.Expression();
        assertEquals(Plus.class, result.getClass());
    }

    @Test()
    public void parsesMinus() throws ParseException {
        MiniJavaParser parser = parserFor("0 - 1");
        Exp result = parser.Expression();
        assertEquals(Minus.class, result.getClass());
    }

    @Test()
    public void parsesTimes() throws ParseException {
        MiniJavaParser parser = parserFor("0 * 1");
        Exp result = parser.Expression();
        assertEquals(Times.class, result.getClass());
    }

    private MiniJavaParser parserFor(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        return new MiniJavaParser(in);
    }
}