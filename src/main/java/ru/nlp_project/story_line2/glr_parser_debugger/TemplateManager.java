package ru.nlp_project.story_line2.glr_parser_debugger;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import ru.nlp_project.story_line2.glr_parser.GLRParser.Sentence;
import ru.nlp_project.story_line2.glr_parser.GrammarManager.GrammarDirectiveTypes;
import ru.nlp_project.story_line2.glr_parser.Interpreter.Fact;
import ru.nlp_project.story_line2.glr_parser.Interpreter.FactField;
import ru.nlp_project.story_line2.glr_parser.ParseTreeNode;
import ru.nlp_project.story_line2.glr_parser.ParseTreeValidator.ParseTreeValidationException;
import ru.nlp_project.story_line2.glr_parser.SentenceProcessingContext;
import ru.nlp_project.story_line2.glr_parser.Token;
import ru.nlp_project.story_line2.glr_parser.eval.Grammar;
import ru.nlp_project.story_line2.glr_parser.keywords.IKeywordEntrance;

class TemplateManager {

  private static final String UTF_8 = "UTF-8";
  private static final String PATH_PREFIX =
      "ru/nlp_project/story_line2/glr_parser_debugger/template/";

  private VelocityEngine engine;
  private long start;

  public String detectedOptimalKwEntrances(SentenceProcessingContext context,
      List<? extends IKeywordEntrance> entrances) {
    VelocityContext velocityContext = new VelocityContext();

    List<Map<String, Object>> list = entrances.stream().map(e -> {
      Map<String, Object> map = new HashMap<>();
      map.put("className", e.getClass().getSimpleName());
      map.put("from", e.getFrom());
      map.put("length", e.getLength());
      map.put("value", StringEscapeUtils.escapeHtml(e.toString()));
      return map;
    }).collect(Collectors.toList());
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("entrances", list);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "detectedOptimalKwEntrances.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String detectedParseTree(SentenceProcessingContext context,
      ParseTreeNode parseTreeNode, ParseTreeNode userRootSymbolNode,
      boolean validated, ParseTreeValidationException exception) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("userRootTree",
        StringEscapeUtils.escapeHtml(userRootSymbolNode.toString()));
    velocityContext.put("validated", validated);
    velocityContext.put("exception", exception);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "detectedParseTree.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String endArticleProcessing(SentenceProcessingContext context) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("article", context.getArticle());
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "endArticleProcessing.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String endFactProcessing(SentenceProcessingContext context) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("context", context);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "endFactProcessing.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String endParseTreeProcessing(SentenceProcessingContext context,
      Collection<ParseTreeNode> trees) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("context", context);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "endParseTreeProcessing.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String endSentenceProcessing(Sentence sentence,
      List<String> articles) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("sentence", sentence);
    velocityContext.put("articles", articles);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "endSentenceProcessing.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  String factExtracted(SentenceProcessingContext context, Fact fact,
      Collection<Entry<String, FactField>> factSet) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("context", context);
    velocityContext.put("fact", fact);
    velocityContext.put("factSet", factSet);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "factExtracted.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String footer(long l) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", l);
    velocityContext.put("now", new Date());
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "footer.vm", UTF_8, velocityContext,
        writer);
    return writer.toString();
  }

  public String grammarHasDirectives(SentenceProcessingContext context,
      Map<GrammarDirectiveTypes, Object> directivesMap) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("directives", directivesMap);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "grammarHasDirectives.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String grammarHasGrammarKeywords(SentenceProcessingContext context,
      Collection<String> keywords) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("keywords", keywords);
    velocityContext.put("plain", false);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "grammarHasKeywords.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String grammarHasPlainKeywords(SentenceProcessingContext context,
      Collection<String> plainKeywords) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("keywords", plainKeywords);
    velocityContext.put("plain", true);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "grammarHasKeywords.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String header() {
    VelocityContext velocityContext = new VelocityContext();
    String jQueryContents = "";
    String jQueryUIContents = "";
    try {
      jQueryUIContents = IOUtils.toString(
          Thread.currentThread().getContextClassLoader().getResourceAsStream(
              "ru/nlp_project/story_line2/glr_parser_debugger/script/jquery-ui.min.js"));
      jQueryContents = IOUtils.toString(
          Thread.currentThread().getContextClassLoader().getResourceAsStream(
              "ru/nlp_project/story_line2/glr_parser_debugger/script/jquery-2.2.3.min.js"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    velocityContext.put("jquery", jQueryContents);
    velocityContext.put("jquery_ui", jQueryUIContents);
    velocityContext.put("now", new Date());
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "header.vm", UTF_8, velocityContext,
        writer);
    return writer.toString();
  }

  public void initialize() {
    engine = new VelocityEngine();
    Properties properties = new Properties();
    properties.setProperty("resource.loader", "class");
    properties.setProperty("class.resource.loader.description",
        "Velocity Classpath Resource Loader");
    properties.setProperty("class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    engine.init(properties);

  }

  String printStackTrace(Exception e, String string) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("exception", e);
    velocityContext.put("exception_literal", string);
    velocityContext.put("now", new Date());
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "printStackTrace.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String startArticleProcessing(SentenceProcessingContext context) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("article", context.getArticle());
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "startArticleProcessing.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String startFactProcessing(SentenceProcessingContext context) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("context", context);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "startFactProcessing.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String startGrammarProcessing(SentenceProcessingContext context,
      Grammar grammar) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("grammar", grammar);
    velocityContext.put("projections",
        grammar.getProjections().stream()
            .map(p -> StringEscapeUtils.escapeHtml(p.toString()))
            .collect(Collectors.toList()));
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "startGrammarProcessing.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String startParseTreeProcessing(SentenceProcessingContext context,
      Collection<ParseTreeNode> trees) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("context", context);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "startParseTreeProcessing.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String startSentenceProcessing(Sentence sentence,
      List<String> articles) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("sentence", sentence);
    velocityContext.put("articles", articles);
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "startSentenceProcessing.vm", UTF_8,
        velocityContext, writer);
    return writer.toString();
  }

  public String tokenNamesGenerated(List<Token> tokens) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("tokens", tokens);
    velocityContext.put("cause", "Генерация токенов после поиска ФИО");
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "tokensList.vm", UTF_8, velocityContext,
        writer);
    return writer.toString();
  }

  public String tokensGenerated(List<Token> tokens) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("tokens", tokens);
    velocityContext.put("cause", "Первоначальная генерация токенов");
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "tokensList.vm", UTF_8, velocityContext,
        writer);
    return writer.toString();
  }

  public String tokensModified(SentenceProcessingContext context,
      List<Token> tokens) {
    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("duration", System.currentTimeMillis() - start);
    velocityContext.put("tokens", tokens);
    velocityContext.put("cause", "Токены изменены");
    StringWriter writer = new StringWriter();
    engine.mergeTemplate(PATH_PREFIX + "tokensList.vm", UTF_8, velocityContext,
        writer);
    return writer.toString();
  }

  public void setStartAnalysisTime(long start) {
    this.start = start;
  }

}
