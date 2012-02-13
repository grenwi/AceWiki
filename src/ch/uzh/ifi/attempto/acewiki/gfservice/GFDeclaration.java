// This file is part of AceWiki.
// Copyright 2008-2012, AceWiki developers.
//
// AceWiki is free software: you can redistribute it and/or modify it under the terms of the GNU
// Lesser General Public License as published by the Free Software Foundation, either version 3 of
// the License, or (at your option) any later version.
//
// AceWiki is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
// even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License along with AceWiki. If
// not, see http://www.gnu.org/licenses/.

package ch.uzh.ifi.attempto.acewiki.gfservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Joiner;

import ch.uzh.ifi.attempto.acewiki.core.AbstractSentence;
import ch.uzh.ifi.attempto.acewiki.core.Declaration;
import ch.uzh.ifi.attempto.acewiki.core.OntologyElement;
import ch.uzh.ifi.attempto.acewiki.core.SentenceDetail;
import ch.uzh.ifi.attempto.base.TextContainer;
import ch.uzh.ifi.attempto.base.TextElement;
import ch.uzh.ifi.attempto.gfservice.GfServiceException;

/**
 * This class represents a declaration statement for the GF AceWiki engine.
 * 
 * @author Kaarel Kaljurand
 */
public class GFDeclaration extends AbstractSentence implements Declaration {

	private final GFGrammar gfGrammar;
	private final Map<String, TextContainer> textContainers = new HashMap<String, TextContainer>();

	// TODO: parse state = a single abstract syntax tree
	private String parseState;

	/**
	 * Creates a new GF declaration object from a parse state.
	 * 
	 * @param parseState The parse state.
	 * @param gfGrammar The grammar object.
	 */
	public GFDeclaration(String parseState, GFGrammar gfGrammar) {
		this.parseState = parseState;
		this.gfGrammar = gfGrammar;
	}

	/**
	 * Creates a new GF declaration object from a text in a given language.
	 *
	 * TODO: the input text should probably be in the form of a token list
	 * 
	 * @param text The declaration text.
	 * @param language The language.
	 * @param gfGrammar The grammar object.
	 */
	public GFDeclaration(String text, String language, GFGrammar gfGrammar) {
		// TODO: quick and ugly hack to be able to move on
		text = text.replaceAll("\\.", " .");
		this.gfGrammar = gfGrammar;
		try {
			Set<String> trees = getGFGrammar().parse(text, language);
			if (trees.isEmpty()) {
				parseState = "";
			} else {
				parseState = trees.iterator().next();
			}
		} catch (GfServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected TextContainer getTextContainer(String language) {
		TextContainer tc = textContainers.get(language);
		if (tc == null) {
			tc = new TextContainer();
			try {
				for (String s : getGFGrammar().linearizeAsTokens(parseState, language)) {
					tc.addElement(new TextElement(s));
				}
			} catch (GfServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textContainers.put(language, tc);
		}
		return tc;
	}

	public List<TextElement> getTextElements(String language) {
		return getTextContainer(language).getTextElements();
	}

	public boolean contains(OntologyElement e) {
		// TODO
		return false;
	}


	/**
	 * <p>Returns the details of this sentence:</p>
	 * <ul>
	 * <li>language;</li>
	 * <li>abstract tree;</li>
	 * <li>TODO: translations;</li>
	 * <li>TODO: word alignment;</li>
	 * <li>TODO: parse tree;</li>
	 * <li>...</li>
	 * </ul>
	 * <p>TODO: everything should be hyperlinked.</p>
	 */
	public List<SentenceDetail> getDetails(String language) {
		List<SentenceDetail> l = new ArrayList<SentenceDetail>();
		l.add(new SentenceDetail(
				"Language",
				"<pre>" + language + "</pre>"
				));
		l.add(new SentenceDetail(
				"Abstract tree",
				"<pre>" + parseState + "</pre>"
				));
		try {
			Map<String, Set<String>> m = getGFGrammar().linearize(parseState);
			StringBuilder sb = new StringBuilder();
			sb.append("<ul>");
			for (String key : m.keySet()) {
				if (! key.equals(language)) {
					sb.append("<li><b>" + key + "</b>: " + m.get(key) + "</li>");
				}
			}
			sb.append("</ul>");
			l.add(new SentenceDetail(
					"Translations",
					sb.toString()
					));
		} catch (GfServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return l;
	}


	public boolean isReasonable() {
		return true;
	}

	public void update() {
	}

	public String serialize() {
		try {
			return getGFGrammar().serialize(parseState);
		} catch (GfServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the grammar object.
	 * 
	 * @return The grammar object.
	 */
	public GFGrammar getGFGrammar() {
		return gfGrammar;
	}

}
