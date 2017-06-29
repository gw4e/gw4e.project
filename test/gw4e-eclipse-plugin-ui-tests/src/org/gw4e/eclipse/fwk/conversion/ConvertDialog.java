package org.gw4e.eclipse.fwk.conversion;

/*-
 * #%L
 * gw4e
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2017 gw4e-project
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.wizard.convert.FolderSelectionGroup;
import org.gw4e.eclipse.wizard.convert.page.ConvertToResourceUIPage;

public class ConvertDialog {
	SWTBot bot;
	SWTBotShell shell;
	
	public ConvertDialog(SWTBotShell shell) {
		super();
		this.shell = shell;
		this.bot = shell.bot();
	}
	

	
	public void prepare  (String project,String packageRootFragment, String pkg,String targetFilename,String targetFormat,String checkTestBox){
		String [] nodes = 	null;
		if (pkg==null) {
			nodes =	new String []  {packageRootFragment} ;
		} else {
			nodes =	new String []  {packageRootFragment,pkg} ;
		}
		final String [] nodeToExpand = nodes;
		ICondition nodeSelectedCondition = new DefaultCondition () {
			 
			@Override
			public boolean test() throws Exception {
				try {
					SWTBotRadio button  = bot.radioWithId(ConvertToResourceUIPage.GW4E_CONVERSION_CHOICE_CHECKBOX_ID, targetFormat);
					button.setFocus();
					button.click();
					 
					SWTBotTree tree = bot.treeWithId(FolderSelectionGroup.GW4E_CONVERSION_TREE,
							FolderSelectionGroup.GW4E_CONVERSION_TREE);
					if (tree.isEnabled()) {
						tree.setFocus();
						SWTBotTreeItem item = tree.expandNode(nodeToExpand);
						item.setFocus();
						item.select();
					}
					return true;
				} catch (Throwable e) {
					return false;
				}
			}

			@Override
			public String getFailureMessage() {
				String nodeMsg = "";
				for (int i = 0; i < nodeToExpand.length; i++) {
					nodeMsg = nodeMsg + "/" + nodeToExpand[i] ;
				}
				String msg = "Unable to get the button " + targetFormat + " checked or unable to  expand nodes " + nodeMsg ;
			 
				return msg;
			}
		};
		
		bot.waitUntil(nodeSelectedCondition);
		
		SWTBotText textFile = bot.textWithLabel("Convert to file");
		if(textFile.isEnabled()) {
			textFile.setText(targetFilename);
		}
		
		SWTBotRadio button  = bot.radioWithId(ConvertToResourceUIPage.GW4E_CONVERSION_CHOICE_CHECKBOX_ID, targetFormat);
		bot.waitUntil(new ICondition(){
			@Override
			public boolean test() throws Exception {
				return  button.isSelected() &&  textFile.getText().equalsIgnoreCase(targetFilename)
						&& button.getText().trim().equalsIgnoreCase(checkTestBox.trim());
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Oops text field not correctly set ";
			}
			
		});
	}
	
	public boolean create (String project,String packageRootFragment, String pkg,String targetFilename,String targetFormat,String checkTestBox,boolean finish) {
		try {
			prepare  (project,packageRootFragment,pkg,targetFilename,targetFormat,  checkTestBox);
			if (!finish) return true;
			SWTBotButton fbutton  = bot.button("Finish");
			fbutton.click();
			bot.waitUntil(Conditions.shellCloses(this.shell), 10 * SWTBotPreferences.TIMEOUT);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}
	
	public boolean create (String project,String packageRootFragment, String pkg,String targetFilename,String targetFormat,String checkTestBox) {
		return create (project,packageRootFragment,pkg,targetFilename,targetFormat,checkTestBox,true);
	}
		
	public boolean createExisting (String project,String packageRootFragment, String pkg,String targetFilename,String targetFormat,String checkTestBox) {
		try {
			prepare  (project,packageRootFragment,pkg,targetFilename,targetFormat,checkTestBox);
		} catch (TimeoutException e) {
			SWTBotShell shell = bot.shell("GW4E Conversion File");
			shell.bot().button("Cancel").click();
			bot.waitUntil(Conditions.shellCloses(shell));
			return false;
		}
		
		SWTBotButton fbutton  = bot.button("Finish");
		assertFalse("Invalid button state ",fbutton.isEnabled());
	
	 
		SWTBotText text = bot.text(" " + MessageUtil.getString("convertto_fileAlreadyExists"));
		
		SWTBotCheckBox eraseButton  = bot.checkBoxWithId(ConvertToResourceUIPage.GW4E_CONVERSION_CHOICE_CHECKBOX_ID, ConvertToResourceUIPage.GW4E_CONVERSION_CHOICE_ERASE_CHECKBOX);
		eraseButton.click();
		
		bot.waitUntil(new ICondition(){
			@Override
			public boolean test() throws Exception {
				return eraseButton.isChecked();
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Oops erase button cannot be selected ";
			}
			
		});
		
		fbutton  = bot.button("Finish");
		assertTrue("Invalid button state ",fbutton.isEnabled());
		fbutton.click();
		
		bot.waitUntil(Conditions.shellCloses(this.shell));
	
		return true;
	}


	/**
	 * @return the shell
	 */
	public SWTBotShell getShell() {
		return shell;
	}
	
}
