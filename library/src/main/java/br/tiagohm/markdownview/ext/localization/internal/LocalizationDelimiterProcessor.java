package br.tiagohm.markdownview.ext.localization.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.internal.Delimiter;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.delimiter.DelimiterRun;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import br.tiagohm.markdownview.ext.localization.Localization;

public class LocalizationDelimiterProcessor implements DelimiterProcessor
{

  @Override
  public char getOpeningCharacter()
  {
    return '{';
  }

  @Override
  public char getClosingCharacter()
  {
    return '}';
  }

  @Override
  public int getMinLength()
  {
    return 2;
  }

  @Override
  public Node unmatchedDelimiterNode(InlineParser inlineParser, final DelimiterRun delimiter)
  {
    return null;
  }

  @Override
  public int getDelimiterUse(DelimiterRun opener, DelimiterRun closer)
  {
    if(opener.length() == 2 && closer.length() == 2)
    {
      return 2;
    }
    else
    {
      return 0;
    }
  }

  @Override
  public void process(Delimiter opener, Delimiter closer, int delimitersUsed)
  {
    Localization loc = new Localization(opener.getTailChars(delimitersUsed), BasedSequence.NULL, closer.getLeadChars(delimitersUsed));
    opener.moveNodesBetweenDelimitersTo(loc, closer);
  }

  @Override
  public boolean canBeOpener(boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace)
  {
    return leftFlanking;
  }

  @Override
  public boolean canBeCloser(boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace)
  {
    return rightFlanking;
  }
}
