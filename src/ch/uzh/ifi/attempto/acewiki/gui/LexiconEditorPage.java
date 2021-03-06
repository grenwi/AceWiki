// This file is part of AceWiki.
// Copyright 2013, AceWiki developers.
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

package ch.uzh.ifi.attempto.acewiki.gui;

import nextapp.echo.app.Insets;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import ch.uzh.ifi.attempto.acewiki.Wiki;
import ch.uzh.ifi.attempto.acewiki.gf.GfLexiconEditor;
import ch.uzh.ifi.attempto.acewiki.gf.GfLexiconEditorModel;
import ch.uzh.ifi.attempto.echocomp.LocaleResources;
import ch.uzh.ifi.attempto.echocomp.VSpace;

// TODO Get rid of gf package dependency or move to gf package

public class LexiconEditorPage extends WikiPage implements ActionListener {

	private static final long serialVersionUID = -701315053787899245L;
	private static final Insets INSETS = new Insets(10, 10, 10, 40);

	private final Wiki mWiki;
	private final Title mTitle;
	private final GfLexiconEditor mGfLexiconEditor;

	public LexiconEditorPage(Wiki wiki) {
		super(wiki);
		mWiki = wiki;
		mTitle = new Title("", true);
		mGfLexiconEditor = new GfLexiconEditor(mWiki, new GfLexiconEditorModel(mWiki.getOntology(), mWiki.getLanguage()));
		mGfLexiconEditor.setInsets(INSETS);

		add(mTitle);
		addHorizontalLine();
		add(new VSpace(10));
		add(mGfLexiconEditor);
	}


	protected void doUpdate() {
		setTabRow(TabRow.getMainTabRow(TabRow.TAB_LEXICON, mWiki));
		mTitle.setText(LocaleResources.getString("acewiki_page_lexicon"));
		mGfLexiconEditor.setModel(new GfLexiconEditorModel(mWiki.getOntology(), mWiki.getLanguage()));
	}


	public boolean equals(Object obj) {
		return obj instanceof LexiconEditorPage;
	}


	public String toString() {
		return "-LEXICON-";
	}


	@Override
	public void actionPerformed(ActionEvent evt) {
	}
}