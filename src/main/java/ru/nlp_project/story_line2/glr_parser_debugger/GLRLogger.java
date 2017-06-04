package ru.nlp_project.story_line2.glr_parser_debugger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.nlp_project.story_line2.glr_parser.GLRParser.Sentence;
import ru.nlp_project.story_line2.glr_parser.GrammarManagerImpl.GrammarDirectiveTypes;
import ru.nlp_project.story_line2.glr_parser.IGLRLogger;
import ru.nlp_project.story_line2.glr_parser.ParseTreeNode;
import ru.nlp_project.story_line2.glr_parser.ParseTreeValidator.ParseTreeValidationException;
import ru.nlp_project.story_line2.glr_parser.SentenceProcessingContext;
import ru.nlp_project.story_line2.glr_parser.Token;
import ru.nlp_project.story_line2.glr_parser.eval.Grammar;
import ru.nlp_project.story_line2.glr_parser.keywords.IKeywordEntrance;

class GLRLogger implements IGLRLogger {

  private WebViewManager webViewManager;
  private TemplateManager templateManager;

  private Map<String, Boolean> flags = new HashMap<>();

  public GLRLogger(WebViewManager webViewManager,
      TemplateManager templateManager) {
    this.webViewManager = webViewManager;
    this.templateManager = templateManager;
  }

  @Override
  public void detectedOptimalKwEntrances(SentenceProcessingContext context,
      List<? extends IKeywordEntrance> optimalCoverage) {
    if (optimalCoverage.isEmpty())
      return;
    String content =
        templateManager.detectedOptimalKwEntrances(context, optimalCoverage);
    webViewManager.appendContent(content);
  }

  @Override
  public void detectedParseTree(SentenceProcessingContext context,
      ParseTreeNode parseTreeNode, ParseTreeNode userRootSymbolNode,
      boolean validated, ParseTreeValidationException exception) {
    String content = templateManager.detectedParseTree(context, parseTreeNode,
        userRootSymbolNode, validated, exception);
    webViewManager.appendContent(content);
  }

  @Override
  public void endArticleProcessing(SentenceProcessingContext context) {
    String content = templateManager.endArticleProcessing(context);
    webViewManager.appendContent(content);
  }

  @Override
  public void endFactProcessing(SentenceProcessingContext context) {
    String content = templateManager.endFactProcessing(context);
    webViewManager.appendContent(content);
  }

  @Override
  public void endParseTreeProcessing(SentenceProcessingContext context,
      Collection<ParseTreeNode> trees) {
    String content = templateManager.endParseTreeProcessing(context, trees);
    webViewManager.appendContent(content);
  }

  @Override
  public void endSentenceProcessing(Sentence sentence, List<String> articles) {
    String content = templateManager.endSentenceProcessing(sentence, articles);
    webViewManager.appendContent(content);
  }

  @Override
  public void grammarHasDirectives(SentenceProcessingContext context,
      Map<GrammarDirectiveTypes, Object> directivesMap) {
    if (directivesMap.isEmpty())
      return;
    String content =
        templateManager.grammarHasDirectives(context, directivesMap);
    webViewManager.appendContent(content);
  }

  @Override
  public void grammarHasGrammarKeywords(SentenceProcessingContext context,
      Collection<String> grammars) {
    if (grammars.isEmpty())
      return;
    String content =
        templateManager.grammarHasGrammarKeywords(context, grammars);
    webViewManager.appendContent(content);
  }

  @Override
  public void grammarHasPlainKeywords(SentenceProcessingContext context,
      Collection<String> plainKws) {
    if (plainKws.isEmpty())
      return;
    String content = templateManager.grammarHasPlainKeywords(context, plainKws);
    webViewManager.appendContent(content);
  }

  public void printStackTrace(Exception e) {
    StringWriter writer = new StringWriter();
    PrintWriter pw = new PrintWriter(writer);
    e.printStackTrace(pw);
    pw.flush();
    String content = templateManager.printStackTrace(e, writer.toString());
    webViewManager.appendContent(content);
  }

  public void reset() {
    flags.clear();
  }

  @Override
  public void startArticleProcessing(SentenceProcessingContext context) {
    String content = templateManager.startArticleProcessing(context);
    webViewManager.appendContent(content);
  }

  @Override
  public void startFactProcessing(SentenceProcessingContext context) {
    String content = templateManager.startFactProcessing(context);
    webViewManager.appendContent(content);
  }

  @Override
  public void startGrammarProcessing(SentenceProcessingContext context,
      Grammar grammar) {
    String content = templateManager.startGrammarProcessing(context, grammar);
    webViewManager.appendContent(content);
  }

  @Override
  public void startParseTreeProcessing(SentenceProcessingContext context,
      Collection<ParseTreeNode> trees) {
    String content = templateManager.startParseTreeProcessing(context, trees);
    webViewManager.appendContent(content);
  }

  @Override
  public void startSentenceProcessing(Sentence sentence,
      List<String> articles) {
    String content =
        templateManager.startSentenceProcessing(sentence, articles);
    webViewManager.appendContent(content);
  }

  @Override
  public void tokenNamesGenerated(List<Token> tokens) {
    String content = templateManager.tokenNamesGenerated(tokens);
    webViewManager.appendContent(content);
  }

  @Override
  public void tokensGenerated(List<Token> tokens) {
    String content = templateManager.tokensGenerated(tokens);
    webViewManager.appendContent(content);
  }

  @Override
  public void tokensModified(SentenceProcessingContext context,
      List<Token> tokens) {
    String content = templateManager.tokensModified(context, tokens);
    webViewManager.appendContent(content);
  }

}
