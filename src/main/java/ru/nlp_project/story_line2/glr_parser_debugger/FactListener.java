package ru.nlp_project.story_line2.glr_parser_debugger;

import ru.nlp_project.story_line2.glr_parser.IFactListener;
import ru.nlp_project.story_line2.glr_parser.Interpreter.Fact;
import ru.nlp_project.story_line2.glr_parser.SentenceProcessingContext;

class FactListener implements IFactListener {

  private WebViewManager webViewManager;
  private TemplateManager templateManager;

  public FactListener(WebViewManager webViewManager,
      TemplateManager templateManager) {
    this.webViewManager = webViewManager;
    this.templateManager = templateManager;
  }

  @Override
  public void factExtracted(SentenceProcessingContext context, Fact fact) {
    String content = templateManager.factExtracted(context, fact,
        fact.getFieldsMap().entrySet());
    webViewManager.appendContent(content);
  }

}
