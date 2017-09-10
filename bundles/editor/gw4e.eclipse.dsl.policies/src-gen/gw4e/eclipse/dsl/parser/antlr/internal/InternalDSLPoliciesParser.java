package gw4e.eclipse.dsl.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import gw4e.eclipse.dsl.services.DSLPoliciesGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalDSLPoliciesParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_NEWLINE", "RULE_FORMAT_COMMENT", "RULE_ID", "RULE_SEV", "RULE_WS", "RULE_INT", "RULE_STRING", "RULE_HEX", "RULE_DECIMAL", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_ANY_OTHER", "'='", "'.json'", "'.graphml'", "'nocheck'", "'sync'", "' '", "';'", "'('", "')'", "'random'", "'quick_random'", "'a_star'", "'weighted_random'", "'time_duration'", "'dependency_edge_coverage'", "'edge_coverage'", "'vertex_coverage'", "'requirement_coverage'", "'reached_vertex'", "'reached_edge'", "'+='", "'-='", "'*='", "'/='", "'%='", "'<'", "'>'", "'>='", "'||'", "'&&'", "'=='", "'!='", "'==='", "'!=='", "'instanceof'", "'->'", "'..<'", "'..'", "'=>'", "'<>'", "'?:'", "'+'", "'-'", "'*'", "'**'", "'/'", "'%'", "'!'", "'as'", "'++'", "'--'", "'.'", "'::'", "'?.'", "','", "'#'", "'{'", "'}'", "'['", "']'", "'|'", "'if'", "'else'", "'switch'", "':'", "'default'", "'case'", "'for'", "'while'", "'do'", "'var'", "'val'", "'extends'", "'static'", "'import'", "'extension'", "'super'", "'new'", "'false'", "'true'", "'null'", "'typeof'", "'throw'", "'return'", "'try'", "'finally'", "'synchronized'", "'catch'", "'?'", "'&'"
    };
    public static final int RULE_HEX=11;
    public static final int T__50=50;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=6;
    public static final int RULE_INT=9;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=13;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int RULE_NEWLINE=4;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int T__19=19;
    public static final int RULE_FORMAT_COMMENT=5;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_DECIMAL=12;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int RULE_SEV=7;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=10;
    public static final int RULE_SL_COMMENT=14;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=8;
    public static final int RULE_ANY_OTHER=15;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int T__86=86;
    public static final int T__87=87;
    public static final int T__105=105;

    // delegates
    // delegators


        public InternalDSLPoliciesParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalDSLPoliciesParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalDSLPoliciesParser.tokenNames; }
    public String getGrammarFileName() { return "InternalDSLPolicies.g"; }



     	private DSLPoliciesGrammarAccess grammarAccess;

        public InternalDSLPoliciesParser(TokenStream input, DSLPoliciesGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected DSLPoliciesGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalDSLPolicies.g:64:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalDSLPolicies.g:64:46: (iv_ruleModel= ruleModel EOF )
            // InternalDSLPolicies.g:65:2: iv_ruleModel= ruleModel EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModelRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModel; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalDSLPolicies.g:71:1: ruleModel returns [EObject current=null] : ( ( ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )? ( (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) ) )+ ) | (this_NEWLINE_4= RULE_NEWLINE )* ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        Token this_NEWLINE_0=null;
        Token this_NEWLINE_2=null;
        Token this_NEWLINE_4=null;
        EObject lv_graphPolicies_3_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:77:2: ( ( ( ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )? ( (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) ) )+ ) | (this_NEWLINE_4= RULE_NEWLINE )* ) )
            // InternalDSLPolicies.g:78:2: ( ( ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )? ( (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) ) )+ ) | (this_NEWLINE_4= RULE_NEWLINE )* )
            {
            // InternalDSLPolicies.g:78:2: ( ( ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )? ( (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) ) )+ ) | (this_NEWLINE_4= RULE_NEWLINE )* )
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // InternalDSLPolicies.g:79:3: ( ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )? ( (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) ) )+ )
                    {
                    // InternalDSLPolicies.g:79:3: ( ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )? ( (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) ) )+ )
                    // InternalDSLPolicies.g:80:4: ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )? ( (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) ) )+
                    {
                    // InternalDSLPolicies.g:80:4: ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )?
                    int alt2=2;
                    alt2 = dfa2.predict(input);
                    switch (alt2) {
                        case 1 :
                            // InternalDSLPolicies.g:81:5: (this_NEWLINE_0= RULE_NEWLINE )* ruleComment
                            {
                            // InternalDSLPolicies.g:81:5: (this_NEWLINE_0= RULE_NEWLINE )*
                            loop1:
                            do {
                                int alt1=2;
                                int LA1_0 = input.LA(1);

                                if ( (LA1_0==RULE_NEWLINE) ) {
                                    alt1=1;
                                }


                                switch (alt1) {
                            	case 1 :
                            	    // InternalDSLPolicies.g:82:6: this_NEWLINE_0= RULE_NEWLINE
                            	    {
                            	    this_NEWLINE_0=(Token)match(input,RULE_NEWLINE,FOLLOW_3); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(this_NEWLINE_0, grammarAccess.getModelAccess().getNEWLINETerminalRuleCall_0_0_0());
                            	      					
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop1;
                                }
                            } while (true);

                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getModelAccess().getCommentParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_4);
                            ruleComment();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;

                    }

                    // InternalDSLPolicies.g:95:4: ( (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) ) )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==RULE_NEWLINE||LA4_0==RULE_ID) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:96:5: (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) )
                    	    {
                    	    // InternalDSLPolicies.g:96:5: (this_NEWLINE_2= RULE_NEWLINE )*
                    	    loop3:
                    	    do {
                    	        int alt3=2;
                    	        int LA3_0 = input.LA(1);

                    	        if ( (LA3_0==RULE_NEWLINE) ) {
                    	            alt3=1;
                    	        }


                    	        switch (alt3) {
                    	    	case 1 :
                    	    	    // InternalDSLPolicies.g:97:6: this_NEWLINE_2= RULE_NEWLINE
                    	    	    {
                    	    	    this_NEWLINE_2=(Token)match(input,RULE_NEWLINE,FOLLOW_4); if (state.failed) return current;
                    	    	    if ( state.backtracking==0 ) {

                    	    	      						newLeafNode(this_NEWLINE_2, grammarAccess.getModelAccess().getNEWLINETerminalRuleCall_0_1_0());
                    	    	      					
                    	    	    }

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop3;
                    	        }
                    	    } while (true);

                    	    // InternalDSLPolicies.g:102:5: ( (lv_graphPolicies_3_0= ruleGraphPolicies ) )
                    	    // InternalDSLPolicies.g:103:6: (lv_graphPolicies_3_0= ruleGraphPolicies )
                    	    {
                    	    // InternalDSLPolicies.g:103:6: (lv_graphPolicies_3_0= ruleGraphPolicies )
                    	    // InternalDSLPolicies.g:104:7: lv_graphPolicies_3_0= ruleGraphPolicies
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getModelAccess().getGraphPoliciesGraphPoliciesParserRuleCall_0_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_5);
                    	    lv_graphPolicies_3_0=ruleGraphPolicies();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getModelRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"graphPolicies",
                    	      								lv_graphPolicies_3_0,
                    	      								"gw4e.eclipse.dsl.DSLPolicies.GraphPolicies");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:124:3: (this_NEWLINE_4= RULE_NEWLINE )*
                    {
                    // InternalDSLPolicies.g:124:3: (this_NEWLINE_4= RULE_NEWLINE )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==RULE_NEWLINE) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:125:4: this_NEWLINE_4= RULE_NEWLINE
                    	    {
                    	    this_NEWLINE_4=(Token)match(input,RULE_NEWLINE,FOLLOW_6); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      				newLeafNode(this_NEWLINE_4, grammarAccess.getModelAccess().getNEWLINETerminalRuleCall_1());
                    	      			
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleComment"
    // InternalDSLPolicies.g:134:1: entryRuleComment returns [String current=null] : iv_ruleComment= ruleComment EOF ;
    public final String entryRuleComment() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleComment = null;


        try {
            // InternalDSLPolicies.g:134:47: (iv_ruleComment= ruleComment EOF )
            // InternalDSLPolicies.g:135:2: iv_ruleComment= ruleComment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCommentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleComment=ruleComment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleComment.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleComment"


    // $ANTLR start "ruleComment"
    // InternalDSLPolicies.g:141:1: ruleComment returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_FORMAT_COMMENT_0= RULE_FORMAT_COMMENT ;
    public final AntlrDatatypeRuleToken ruleComment() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_FORMAT_COMMENT_0=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:147:2: (this_FORMAT_COMMENT_0= RULE_FORMAT_COMMENT )
            // InternalDSLPolicies.g:148:2: this_FORMAT_COMMENT_0= RULE_FORMAT_COMMENT
            {
            this_FORMAT_COMMENT_0=(Token)match(input,RULE_FORMAT_COMMENT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_FORMAT_COMMENT_0);
              	
            }
            if ( state.backtracking==0 ) {

              		newLeafNode(this_FORMAT_COMMENT_0, grammarAccess.getCommentAccess().getFORMAT_COMMENTTerminalRuleCall());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComment"


    // $ANTLR start "entryRuleGraphPolicies"
    // InternalDSLPolicies.g:158:1: entryRuleGraphPolicies returns [EObject current=null] : iv_ruleGraphPolicies= ruleGraphPolicies EOF ;
    public final EObject entryRuleGraphPolicies() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGraphPolicies = null;


        try {
            // InternalDSLPolicies.g:158:54: (iv_ruleGraphPolicies= ruleGraphPolicies EOF )
            // InternalDSLPolicies.g:159:2: iv_ruleGraphPolicies= ruleGraphPolicies EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getGraphPoliciesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleGraphPolicies=ruleGraphPolicies();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleGraphPolicies; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGraphPolicies"


    // $ANTLR start "ruleGraphPolicies"
    // InternalDSLPolicies.g:165:1: ruleGraphPolicies returns [EObject current=null] : ( ( (lv_graphModelPolicies_0_0= ruleModelName ) ) otherlv_1= '=' ( (lv_policies_2_0= rulePolicies ) )+ ) ;
    public final EObject ruleGraphPolicies() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_graphModelPolicies_0_0 = null;

        EObject lv_policies_2_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:171:2: ( ( ( (lv_graphModelPolicies_0_0= ruleModelName ) ) otherlv_1= '=' ( (lv_policies_2_0= rulePolicies ) )+ ) )
            // InternalDSLPolicies.g:172:2: ( ( (lv_graphModelPolicies_0_0= ruleModelName ) ) otherlv_1= '=' ( (lv_policies_2_0= rulePolicies ) )+ )
            {
            // InternalDSLPolicies.g:172:2: ( ( (lv_graphModelPolicies_0_0= ruleModelName ) ) otherlv_1= '=' ( (lv_policies_2_0= rulePolicies ) )+ )
            // InternalDSLPolicies.g:173:3: ( (lv_graphModelPolicies_0_0= ruleModelName ) ) otherlv_1= '=' ( (lv_policies_2_0= rulePolicies ) )+
            {
            // InternalDSLPolicies.g:173:3: ( (lv_graphModelPolicies_0_0= ruleModelName ) )
            // InternalDSLPolicies.g:174:4: (lv_graphModelPolicies_0_0= ruleModelName )
            {
            // InternalDSLPolicies.g:174:4: (lv_graphModelPolicies_0_0= ruleModelName )
            // InternalDSLPolicies.g:175:5: lv_graphModelPolicies_0_0= ruleModelName
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getGraphPoliciesAccess().getGraphModelPoliciesModelNameParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_7);
            lv_graphModelPolicies_0_0=ruleModelName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getGraphPoliciesRule());
              					}
              					set(
              						current,
              						"graphModelPolicies",
              						lv_graphModelPolicies_0_0,
              						"gw4e.eclipse.dsl.DSLPolicies.ModelName");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getGraphPoliciesAccess().getEqualsSignKeyword_1());
              		
            }
            // InternalDSLPolicies.g:196:3: ( (lv_policies_2_0= rulePolicies ) )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=19 && LA7_0<=20)||(LA7_0>=25 && LA7_0<=28)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalDSLPolicies.g:197:4: (lv_policies_2_0= rulePolicies )
            	    {
            	    // InternalDSLPolicies.g:197:4: (lv_policies_2_0= rulePolicies )
            	    // InternalDSLPolicies.g:198:5: lv_policies_2_0= rulePolicies
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getGraphPoliciesAccess().getPoliciesPoliciesParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_policies_2_0=rulePolicies();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getGraphPoliciesRule());
            	      					}
            	      					add(
            	      						current,
            	      						"policies",
            	      						lv_policies_2_0,
            	      						"gw4e.eclipse.dsl.DSLPolicies.Policies");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGraphPolicies"


    // $ANTLR start "entryRuleModelName"
    // InternalDSLPolicies.g:219:1: entryRuleModelName returns [String current=null] : iv_ruleModelName= ruleModelName EOF ;
    public final String entryRuleModelName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleModelName = null;


        try {
            // InternalDSLPolicies.g:219:49: (iv_ruleModelName= ruleModelName EOF )
            // InternalDSLPolicies.g:220:2: iv_ruleModelName= ruleModelName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModelNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleModelName=ruleModelName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModelName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModelName"


    // $ANTLR start "ruleModelName"
    // InternalDSLPolicies.g:226:1: ruleModelName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ID_0= RULE_ID kw= '.json' ) | (this_ID_2= RULE_ID kw= '.graphml' ) ) ;
    public final AntlrDatatypeRuleToken ruleModelName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:232:2: ( ( (this_ID_0= RULE_ID kw= '.json' ) | (this_ID_2= RULE_ID kw= '.graphml' ) ) )
            // InternalDSLPolicies.g:233:2: ( (this_ID_0= RULE_ID kw= '.json' ) | (this_ID_2= RULE_ID kw= '.graphml' ) )
            {
            // InternalDSLPolicies.g:233:2: ( (this_ID_0= RULE_ID kw= '.json' ) | (this_ID_2= RULE_ID kw= '.graphml' ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_ID) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==18) ) {
                    alt8=2;
                }
                else if ( (LA8_1==17) ) {
                    alt8=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalDSLPolicies.g:234:3: (this_ID_0= RULE_ID kw= '.json' )
                    {
                    // InternalDSLPolicies.g:234:3: (this_ID_0= RULE_ID kw= '.json' )
                    // InternalDSLPolicies.g:235:4: this_ID_0= RULE_ID kw= '.json'
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_ID_0, grammarAccess.getModelNameAccess().getIDTerminalRuleCall_0_0());
                      			
                    }
                    kw=(Token)match(input,17,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getModelNameAccess().getJsonKeyword_0_1());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:249:3: (this_ID_2= RULE_ID kw= '.graphml' )
                    {
                    // InternalDSLPolicies.g:249:3: (this_ID_2= RULE_ID kw= '.graphml' )
                    // InternalDSLPolicies.g:250:4: this_ID_2= RULE_ID kw= '.graphml'
                    {
                    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_ID_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_ID_2, grammarAccess.getModelNameAccess().getIDTerminalRuleCall_1_0());
                      			
                    }
                    kw=(Token)match(input,18,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getModelNameAccess().getGraphmlKeyword_1_1());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModelName"


    // $ANTLR start "entryRuleSeverity"
    // InternalDSLPolicies.g:267:1: entryRuleSeverity returns [EObject current=null] : iv_ruleSeverity= ruleSeverity EOF ;
    public final EObject entryRuleSeverity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSeverity = null;


        try {
            // InternalDSLPolicies.g:267:49: (iv_ruleSeverity= ruleSeverity EOF )
            // InternalDSLPolicies.g:268:2: iv_ruleSeverity= ruleSeverity EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSeverityRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSeverity=ruleSeverity();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSeverity; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSeverity"


    // $ANTLR start "ruleSeverity"
    // InternalDSLPolicies.g:274:1: ruleSeverity returns [EObject current=null] : ( (lv_level_0_0= RULE_SEV ) ) ;
    public final EObject ruleSeverity() throws RecognitionException {
        EObject current = null;

        Token lv_level_0_0=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:280:2: ( ( (lv_level_0_0= RULE_SEV ) ) )
            // InternalDSLPolicies.g:281:2: ( (lv_level_0_0= RULE_SEV ) )
            {
            // InternalDSLPolicies.g:281:2: ( (lv_level_0_0= RULE_SEV ) )
            // InternalDSLPolicies.g:282:3: (lv_level_0_0= RULE_SEV )
            {
            // InternalDSLPolicies.g:282:3: (lv_level_0_0= RULE_SEV )
            // InternalDSLPolicies.g:283:4: lv_level_0_0= RULE_SEV
            {
            lv_level_0_0=(Token)match(input,RULE_SEV,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_level_0_0, grammarAccess.getSeverityAccess().getLevelSEVTerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getSeverityRule());
              				}
              				setWithLastConsumed(
              					current,
              					"level",
              					lv_level_0_0,
              					"gw4e.eclipse.dsl.DSLPolicies.SEV");
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSeverity"


    // $ANTLR start "entryRulePolicies"
    // InternalDSLPolicies.g:302:1: entryRulePolicies returns [EObject current=null] : iv_rulePolicies= rulePolicies EOF ;
    public final EObject entryRulePolicies() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePolicies = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");

        try {
            // InternalDSLPolicies.g:304:2: (iv_rulePolicies= rulePolicies EOF )
            // InternalDSLPolicies.g:305:2: iv_rulePolicies= rulePolicies EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPoliciesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePolicies=rulePolicies();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePolicies; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRulePolicies"


    // $ANTLR start "rulePolicies"
    // InternalDSLPolicies.g:314:1: rulePolicies returns [EObject current=null] : ( ( () ( (lv_nocheck_1_0= 'nocheck' ) ) ) | ( (lv_sync_2_0= 'sync' ) ) | ( ( (lv_pathgenerator_3_0= rulePathGeneratorStopCondition ) ) (otherlv_4= ' ' ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) ) )* otherlv_6= ';' ( (lv_severity_7_0= ruleSeverity ) ) otherlv_8= ';' ) ) ;
    public final EObject rulePolicies() throws RecognitionException {
        EObject current = null;

        Token lv_nocheck_1_0=null;
        Token lv_sync_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_pathgenerator_3_0 = null;

        EObject lv_pathgenerator_5_0 = null;

        EObject lv_severity_7_0 = null;



        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");

        try {
            // InternalDSLPolicies.g:321:2: ( ( ( () ( (lv_nocheck_1_0= 'nocheck' ) ) ) | ( (lv_sync_2_0= 'sync' ) ) | ( ( (lv_pathgenerator_3_0= rulePathGeneratorStopCondition ) ) (otherlv_4= ' ' ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) ) )* otherlv_6= ';' ( (lv_severity_7_0= ruleSeverity ) ) otherlv_8= ';' ) ) )
            // InternalDSLPolicies.g:322:2: ( ( () ( (lv_nocheck_1_0= 'nocheck' ) ) ) | ( (lv_sync_2_0= 'sync' ) ) | ( ( (lv_pathgenerator_3_0= rulePathGeneratorStopCondition ) ) (otherlv_4= ' ' ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) ) )* otherlv_6= ';' ( (lv_severity_7_0= ruleSeverity ) ) otherlv_8= ';' ) )
            {
            // InternalDSLPolicies.g:322:2: ( ( () ( (lv_nocheck_1_0= 'nocheck' ) ) ) | ( (lv_sync_2_0= 'sync' ) ) | ( ( (lv_pathgenerator_3_0= rulePathGeneratorStopCondition ) ) (otherlv_4= ' ' ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) ) )* otherlv_6= ';' ( (lv_severity_7_0= ruleSeverity ) ) otherlv_8= ';' ) )
            int alt10=3;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt10=1;
                }
                break;
            case 20:
                {
                alt10=2;
                }
                break;
            case 25:
            case 26:
            case 27:
            case 28:
                {
                alt10=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalDSLPolicies.g:323:3: ( () ( (lv_nocheck_1_0= 'nocheck' ) ) )
                    {
                    // InternalDSLPolicies.g:323:3: ( () ( (lv_nocheck_1_0= 'nocheck' ) ) )
                    // InternalDSLPolicies.g:324:4: () ( (lv_nocheck_1_0= 'nocheck' ) )
                    {
                    // InternalDSLPolicies.g:324:4: ()
                    // InternalDSLPolicies.g:325:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getPoliciesAccess().getPoliciesAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalDSLPolicies.g:331:4: ( (lv_nocheck_1_0= 'nocheck' ) )
                    // InternalDSLPolicies.g:332:5: (lv_nocheck_1_0= 'nocheck' )
                    {
                    // InternalDSLPolicies.g:332:5: (lv_nocheck_1_0= 'nocheck' )
                    // InternalDSLPolicies.g:333:6: lv_nocheck_1_0= 'nocheck'
                    {
                    lv_nocheck_1_0=(Token)match(input,19,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_nocheck_1_0, grammarAccess.getPoliciesAccess().getNocheckNocheckKeyword_0_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPoliciesRule());
                      						}
                      						setWithLastConsumed(current, "nocheck", true, "nocheck");
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:347:3: ( (lv_sync_2_0= 'sync' ) )
                    {
                    // InternalDSLPolicies.g:347:3: ( (lv_sync_2_0= 'sync' ) )
                    // InternalDSLPolicies.g:348:4: (lv_sync_2_0= 'sync' )
                    {
                    // InternalDSLPolicies.g:348:4: (lv_sync_2_0= 'sync' )
                    // InternalDSLPolicies.g:349:5: lv_sync_2_0= 'sync'
                    {
                    lv_sync_2_0=(Token)match(input,20,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_sync_2_0, grammarAccess.getPoliciesAccess().getSyncSyncKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getPoliciesRule());
                      					}
                      					setWithLastConsumed(current, "sync", true, "sync");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:362:3: ( ( (lv_pathgenerator_3_0= rulePathGeneratorStopCondition ) ) (otherlv_4= ' ' ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) ) )* otherlv_6= ';' ( (lv_severity_7_0= ruleSeverity ) ) otherlv_8= ';' )
                    {
                    // InternalDSLPolicies.g:362:3: ( ( (lv_pathgenerator_3_0= rulePathGeneratorStopCondition ) ) (otherlv_4= ' ' ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) ) )* otherlv_6= ';' ( (lv_severity_7_0= ruleSeverity ) ) otherlv_8= ';' )
                    // InternalDSLPolicies.g:363:4: ( (lv_pathgenerator_3_0= rulePathGeneratorStopCondition ) ) (otherlv_4= ' ' ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) ) )* otherlv_6= ';' ( (lv_severity_7_0= ruleSeverity ) ) otherlv_8= ';'
                    {
                    // InternalDSLPolicies.g:363:4: ( (lv_pathgenerator_3_0= rulePathGeneratorStopCondition ) )
                    // InternalDSLPolicies.g:364:5: (lv_pathgenerator_3_0= rulePathGeneratorStopCondition )
                    {
                    // InternalDSLPolicies.g:364:5: (lv_pathgenerator_3_0= rulePathGeneratorStopCondition )
                    // InternalDSLPolicies.g:365:6: lv_pathgenerator_3_0= rulePathGeneratorStopCondition
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPoliciesAccess().getPathgeneratorPathGeneratorStopConditionParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_12);
                    lv_pathgenerator_3_0=rulePathGeneratorStopCondition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPoliciesRule());
                      						}
                      						add(
                      							current,
                      							"pathgenerator",
                      							lv_pathgenerator_3_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.PathGeneratorStopCondition");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:382:4: (otherlv_4= ' ' ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) ) )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==21) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:383:5: otherlv_4= ' ' ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) )
                    	    {
                    	    otherlv_4=(Token)match(input,21,FOLLOW_8); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_4, grammarAccess.getPoliciesAccess().getSpaceKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalDSLPolicies.g:387:5: ( (lv_pathgenerator_5_0= rulePathGeneratorStopCondition ) )
                    	    // InternalDSLPolicies.g:388:6: (lv_pathgenerator_5_0= rulePathGeneratorStopCondition )
                    	    {
                    	    // InternalDSLPolicies.g:388:6: (lv_pathgenerator_5_0= rulePathGeneratorStopCondition )
                    	    // InternalDSLPolicies.g:389:7: lv_pathgenerator_5_0= rulePathGeneratorStopCondition
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getPoliciesAccess().getPathgeneratorPathGeneratorStopConditionParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_12);
                    	    lv_pathgenerator_5_0=rulePathGeneratorStopCondition();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getPoliciesRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"pathgenerator",
                    	      								lv_pathgenerator_5_0,
                    	      								"gw4e.eclipse.dsl.DSLPolicies.PathGeneratorStopCondition");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,22,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getPoliciesAccess().getSemicolonKeyword_2_2());
                      			
                    }
                    // InternalDSLPolicies.g:411:4: ( (lv_severity_7_0= ruleSeverity ) )
                    // InternalDSLPolicies.g:412:5: (lv_severity_7_0= ruleSeverity )
                    {
                    // InternalDSLPolicies.g:412:5: (lv_severity_7_0= ruleSeverity )
                    // InternalDSLPolicies.g:413:6: lv_severity_7_0= ruleSeverity
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPoliciesAccess().getSeveritySeverityParserRuleCall_2_3_0());
                      					
                    }
                    pushFollow(FOLLOW_14);
                    lv_severity_7_0=ruleSeverity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPoliciesRule());
                      						}
                      						set(
                      							current,
                      							"severity",
                      							lv_severity_7_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.Severity");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_8=(Token)match(input,22,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getPoliciesAccess().getSemicolonKeyword_2_4());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "rulePolicies"


    // $ANTLR start "entryRulePathGeneratorStopCondition"
    // InternalDSLPolicies.g:442:1: entryRulePathGeneratorStopCondition returns [EObject current=null] : iv_rulePathGeneratorStopCondition= rulePathGeneratorStopCondition EOF ;
    public final EObject entryRulePathGeneratorStopCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePathGeneratorStopCondition = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");

        try {
            // InternalDSLPolicies.g:444:2: (iv_rulePathGeneratorStopCondition= rulePathGeneratorStopCondition EOF )
            // InternalDSLPolicies.g:445:2: iv_rulePathGeneratorStopCondition= rulePathGeneratorStopCondition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPathGeneratorStopConditionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePathGeneratorStopCondition=rulePathGeneratorStopCondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePathGeneratorStopCondition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRulePathGeneratorStopCondition"


    // $ANTLR start "rulePathGeneratorStopCondition"
    // InternalDSLPolicies.g:454:1: rulePathGeneratorStopCondition returns [EObject current=null] : ( () ( (lv_algorithmType_1_0= ruleAlgorithmType ) ) otherlv_2= '(' ( ( (lv_stopCondition_3_0= ruleStringCondition ) ) | ( (lv_stopConditionype_4_0= ruleIntegerStopCondition ) ) | ( (lv_stopCondition_5_0= rulePercentageCondition ) ) ) otherlv_6= ')' (this_WS_7= RULE_WS )* ) ;
    public final EObject rulePathGeneratorStopCondition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_6=null;
        Token this_WS_7=null;
        EObject lv_algorithmType_1_0 = null;

        EObject lv_stopCondition_3_0 = null;

        EObject lv_stopConditionype_4_0 = null;

        EObject lv_stopCondition_5_0 = null;



        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");

        try {
            // InternalDSLPolicies.g:461:2: ( ( () ( (lv_algorithmType_1_0= ruleAlgorithmType ) ) otherlv_2= '(' ( ( (lv_stopCondition_3_0= ruleStringCondition ) ) | ( (lv_stopConditionype_4_0= ruleIntegerStopCondition ) ) | ( (lv_stopCondition_5_0= rulePercentageCondition ) ) ) otherlv_6= ')' (this_WS_7= RULE_WS )* ) )
            // InternalDSLPolicies.g:462:2: ( () ( (lv_algorithmType_1_0= ruleAlgorithmType ) ) otherlv_2= '(' ( ( (lv_stopCondition_3_0= ruleStringCondition ) ) | ( (lv_stopConditionype_4_0= ruleIntegerStopCondition ) ) | ( (lv_stopCondition_5_0= rulePercentageCondition ) ) ) otherlv_6= ')' (this_WS_7= RULE_WS )* )
            {
            // InternalDSLPolicies.g:462:2: ( () ( (lv_algorithmType_1_0= ruleAlgorithmType ) ) otherlv_2= '(' ( ( (lv_stopCondition_3_0= ruleStringCondition ) ) | ( (lv_stopConditionype_4_0= ruleIntegerStopCondition ) ) | ( (lv_stopCondition_5_0= rulePercentageCondition ) ) ) otherlv_6= ')' (this_WS_7= RULE_WS )* )
            // InternalDSLPolicies.g:463:3: () ( (lv_algorithmType_1_0= ruleAlgorithmType ) ) otherlv_2= '(' ( ( (lv_stopCondition_3_0= ruleStringCondition ) ) | ( (lv_stopConditionype_4_0= ruleIntegerStopCondition ) ) | ( (lv_stopCondition_5_0= rulePercentageCondition ) ) ) otherlv_6= ')' (this_WS_7= RULE_WS )*
            {
            // InternalDSLPolicies.g:463:3: ()
            // InternalDSLPolicies.g:464:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getPathGeneratorStopConditionAccess().getPathGeneratorStopConditionAction_0(),
              					current);
              			
            }

            }

            // InternalDSLPolicies.g:470:3: ( (lv_algorithmType_1_0= ruleAlgorithmType ) )
            // InternalDSLPolicies.g:471:4: (lv_algorithmType_1_0= ruleAlgorithmType )
            {
            // InternalDSLPolicies.g:471:4: (lv_algorithmType_1_0= ruleAlgorithmType )
            // InternalDSLPolicies.g:472:5: lv_algorithmType_1_0= ruleAlgorithmType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getPathGeneratorStopConditionAccess().getAlgorithmTypeAlgorithmTypeParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_algorithmType_1_0=ruleAlgorithmType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getPathGeneratorStopConditionRule());
              					}
              					set(
              						current,
              						"algorithmType",
              						lv_algorithmType_1_0,
              						"gw4e.eclipse.dsl.DSLPolicies.AlgorithmType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,23,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getPathGeneratorStopConditionAccess().getLeftParenthesisKeyword_2());
              		
            }
            // InternalDSLPolicies.g:493:3: ( ( (lv_stopCondition_3_0= ruleStringCondition ) ) | ( (lv_stopConditionype_4_0= ruleIntegerStopCondition ) ) | ( (lv_stopCondition_5_0= rulePercentageCondition ) ) )
            int alt11=3;
            switch ( input.LA(1) ) {
            case 34:
            case 35:
                {
                alt11=1;
                }
                break;
            case 29:
            case 30:
                {
                alt11=2;
                }
                break;
            case 31:
            case 32:
            case 33:
                {
                alt11=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // InternalDSLPolicies.g:494:4: ( (lv_stopCondition_3_0= ruleStringCondition ) )
                    {
                    // InternalDSLPolicies.g:494:4: ( (lv_stopCondition_3_0= ruleStringCondition ) )
                    // InternalDSLPolicies.g:495:5: (lv_stopCondition_3_0= ruleStringCondition )
                    {
                    // InternalDSLPolicies.g:495:5: (lv_stopCondition_3_0= ruleStringCondition )
                    // InternalDSLPolicies.g:496:6: lv_stopCondition_3_0= ruleStringCondition
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPathGeneratorStopConditionAccess().getStopConditionStringConditionParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_stopCondition_3_0=ruleStringCondition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPathGeneratorStopConditionRule());
                      						}
                      						set(
                      							current,
                      							"stopCondition",
                      							lv_stopCondition_3_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.StringCondition");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:514:4: ( (lv_stopConditionype_4_0= ruleIntegerStopCondition ) )
                    {
                    // InternalDSLPolicies.g:514:4: ( (lv_stopConditionype_4_0= ruleIntegerStopCondition ) )
                    // InternalDSLPolicies.g:515:5: (lv_stopConditionype_4_0= ruleIntegerStopCondition )
                    {
                    // InternalDSLPolicies.g:515:5: (lv_stopConditionype_4_0= ruleIntegerStopCondition )
                    // InternalDSLPolicies.g:516:6: lv_stopConditionype_4_0= ruleIntegerStopCondition
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPathGeneratorStopConditionAccess().getStopConditionypeIntegerStopConditionParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_stopConditionype_4_0=ruleIntegerStopCondition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPathGeneratorStopConditionRule());
                      						}
                      						set(
                      							current,
                      							"stopConditionype",
                      							lv_stopConditionype_4_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.IntegerStopCondition");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:534:4: ( (lv_stopCondition_5_0= rulePercentageCondition ) )
                    {
                    // InternalDSLPolicies.g:534:4: ( (lv_stopCondition_5_0= rulePercentageCondition ) )
                    // InternalDSLPolicies.g:535:5: (lv_stopCondition_5_0= rulePercentageCondition )
                    {
                    // InternalDSLPolicies.g:535:5: (lv_stopCondition_5_0= rulePercentageCondition )
                    // InternalDSLPolicies.g:536:6: lv_stopCondition_5_0= rulePercentageCondition
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPathGeneratorStopConditionAccess().getStopConditionPercentageConditionParserRuleCall_3_2_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_stopCondition_5_0=rulePercentageCondition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPathGeneratorStopConditionRule());
                      						}
                      						set(
                      							current,
                      							"stopCondition",
                      							lv_stopCondition_5_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.PercentageCondition");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,24,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getPathGeneratorStopConditionAccess().getRightParenthesisKeyword_4());
              		
            }
            // InternalDSLPolicies.g:558:3: (this_WS_7= RULE_WS )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==RULE_WS) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalDSLPolicies.g:559:4: this_WS_7= RULE_WS
            	    {
            	    this_WS_7=(Token)match(input,RULE_WS,FOLLOW_18); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_WS_7, grammarAccess.getPathGeneratorStopConditionAccess().getWSTerminalRuleCall_5());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "rulePathGeneratorStopCondition"


    // $ANTLR start "entryRuleAlgorithmType"
    // InternalDSLPolicies.g:571:1: entryRuleAlgorithmType returns [EObject current=null] : iv_ruleAlgorithmType= ruleAlgorithmType EOF ;
    public final EObject entryRuleAlgorithmType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlgorithmType = null;


        try {
            // InternalDSLPolicies.g:571:54: (iv_ruleAlgorithmType= ruleAlgorithmType EOF )
            // InternalDSLPolicies.g:572:2: iv_ruleAlgorithmType= ruleAlgorithmType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAlgorithmTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAlgorithmType=ruleAlgorithmType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAlgorithmType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlgorithmType"


    // $ANTLR start "ruleAlgorithmType"
    // InternalDSLPolicies.g:578:1: ruleAlgorithmType returns [EObject current=null] : ( ( (lv_type_0_0= 'random' ) ) | ( (lv_type_1_0= 'quick_random' ) ) | ( (lv_type_2_0= 'a_star' ) ) | ( (lv_type_3_0= 'weighted_random' ) ) ) ;
    public final EObject ruleAlgorithmType() throws RecognitionException {
        EObject current = null;

        Token lv_type_0_0=null;
        Token lv_type_1_0=null;
        Token lv_type_2_0=null;
        Token lv_type_3_0=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:584:2: ( ( ( (lv_type_0_0= 'random' ) ) | ( (lv_type_1_0= 'quick_random' ) ) | ( (lv_type_2_0= 'a_star' ) ) | ( (lv_type_3_0= 'weighted_random' ) ) ) )
            // InternalDSLPolicies.g:585:2: ( ( (lv_type_0_0= 'random' ) ) | ( (lv_type_1_0= 'quick_random' ) ) | ( (lv_type_2_0= 'a_star' ) ) | ( (lv_type_3_0= 'weighted_random' ) ) )
            {
            // InternalDSLPolicies.g:585:2: ( ( (lv_type_0_0= 'random' ) ) | ( (lv_type_1_0= 'quick_random' ) ) | ( (lv_type_2_0= 'a_star' ) ) | ( (lv_type_3_0= 'weighted_random' ) ) )
            int alt13=4;
            switch ( input.LA(1) ) {
            case 25:
                {
                alt13=1;
                }
                break;
            case 26:
                {
                alt13=2;
                }
                break;
            case 27:
                {
                alt13=3;
                }
                break;
            case 28:
                {
                alt13=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // InternalDSLPolicies.g:586:3: ( (lv_type_0_0= 'random' ) )
                    {
                    // InternalDSLPolicies.g:586:3: ( (lv_type_0_0= 'random' ) )
                    // InternalDSLPolicies.g:587:4: (lv_type_0_0= 'random' )
                    {
                    // InternalDSLPolicies.g:587:4: (lv_type_0_0= 'random' )
                    // InternalDSLPolicies.g:588:5: lv_type_0_0= 'random'
                    {
                    lv_type_0_0=(Token)match(input,25,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_type_0_0, grammarAccess.getAlgorithmTypeAccess().getTypeRandomKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getAlgorithmTypeRule());
                      					}
                      					setWithLastConsumed(current, "type", lv_type_0_0, "random");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:601:3: ( (lv_type_1_0= 'quick_random' ) )
                    {
                    // InternalDSLPolicies.g:601:3: ( (lv_type_1_0= 'quick_random' ) )
                    // InternalDSLPolicies.g:602:4: (lv_type_1_0= 'quick_random' )
                    {
                    // InternalDSLPolicies.g:602:4: (lv_type_1_0= 'quick_random' )
                    // InternalDSLPolicies.g:603:5: lv_type_1_0= 'quick_random'
                    {
                    lv_type_1_0=(Token)match(input,26,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_type_1_0, grammarAccess.getAlgorithmTypeAccess().getTypeQuick_randomKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getAlgorithmTypeRule());
                      					}
                      					setWithLastConsumed(current, "type", lv_type_1_0, "quick_random");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:616:3: ( (lv_type_2_0= 'a_star' ) )
                    {
                    // InternalDSLPolicies.g:616:3: ( (lv_type_2_0= 'a_star' ) )
                    // InternalDSLPolicies.g:617:4: (lv_type_2_0= 'a_star' )
                    {
                    // InternalDSLPolicies.g:617:4: (lv_type_2_0= 'a_star' )
                    // InternalDSLPolicies.g:618:5: lv_type_2_0= 'a_star'
                    {
                    lv_type_2_0=(Token)match(input,27,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_type_2_0, grammarAccess.getAlgorithmTypeAccess().getTypeA_starKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getAlgorithmTypeRule());
                      					}
                      					setWithLastConsumed(current, "type", lv_type_2_0, "a_star");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalDSLPolicies.g:631:3: ( (lv_type_3_0= 'weighted_random' ) )
                    {
                    // InternalDSLPolicies.g:631:3: ( (lv_type_3_0= 'weighted_random' ) )
                    // InternalDSLPolicies.g:632:4: (lv_type_3_0= 'weighted_random' )
                    {
                    // InternalDSLPolicies.g:632:4: (lv_type_3_0= 'weighted_random' )
                    // InternalDSLPolicies.g:633:5: lv_type_3_0= 'weighted_random'
                    {
                    lv_type_3_0=(Token)match(input,28,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_type_3_0, grammarAccess.getAlgorithmTypeAccess().getTypeWeighted_randomKeyword_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getAlgorithmTypeRule());
                      					}
                      					setWithLastConsumed(current, "type", lv_type_3_0, "weighted_random");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlgorithmType"


    // $ANTLR start "entryRuleIntegerStopCondition"
    // InternalDSLPolicies.g:649:1: entryRuleIntegerStopCondition returns [EObject current=null] : iv_ruleIntegerStopCondition= ruleIntegerStopCondition EOF ;
    public final EObject entryRuleIntegerStopCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntegerStopCondition = null;


        try {
            // InternalDSLPolicies.g:649:61: (iv_ruleIntegerStopCondition= ruleIntegerStopCondition EOF )
            // InternalDSLPolicies.g:650:2: iv_ruleIntegerStopCondition= ruleIntegerStopCondition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIntegerStopConditionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIntegerStopCondition=ruleIntegerStopCondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIntegerStopCondition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIntegerStopCondition"


    // $ANTLR start "ruleIntegerStopCondition"
    // InternalDSLPolicies.g:656:1: ruleIntegerStopCondition returns [EObject current=null] : ( ( ( (lv_pathtype_0_0= 'time_duration' ) ) otherlv_1= '(' ( (lv_value_2_0= RULE_INT ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'dependency_edge_coverage' ) ) otherlv_5= '(' ( (lv_value_6_0= RULE_INT ) ) otherlv_7= ')' ) ) ;
    public final EObject ruleIntegerStopCondition() throws RecognitionException {
        EObject current = null;

        Token lv_pathtype_0_0=null;
        Token otherlv_1=null;
        Token lv_value_2_0=null;
        Token otherlv_3=null;
        Token lv_pathtype_4_0=null;
        Token otherlv_5=null;
        Token lv_value_6_0=null;
        Token otherlv_7=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:662:2: ( ( ( ( (lv_pathtype_0_0= 'time_duration' ) ) otherlv_1= '(' ( (lv_value_2_0= RULE_INT ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'dependency_edge_coverage' ) ) otherlv_5= '(' ( (lv_value_6_0= RULE_INT ) ) otherlv_7= ')' ) ) )
            // InternalDSLPolicies.g:663:2: ( ( ( (lv_pathtype_0_0= 'time_duration' ) ) otherlv_1= '(' ( (lv_value_2_0= RULE_INT ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'dependency_edge_coverage' ) ) otherlv_5= '(' ( (lv_value_6_0= RULE_INT ) ) otherlv_7= ')' ) )
            {
            // InternalDSLPolicies.g:663:2: ( ( ( (lv_pathtype_0_0= 'time_duration' ) ) otherlv_1= '(' ( (lv_value_2_0= RULE_INT ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'dependency_edge_coverage' ) ) otherlv_5= '(' ( (lv_value_6_0= RULE_INT ) ) otherlv_7= ')' ) )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==29) ) {
                alt14=1;
            }
            else if ( (LA14_0==30) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // InternalDSLPolicies.g:664:3: ( ( (lv_pathtype_0_0= 'time_duration' ) ) otherlv_1= '(' ( (lv_value_2_0= RULE_INT ) ) otherlv_3= ')' )
                    {
                    // InternalDSLPolicies.g:664:3: ( ( (lv_pathtype_0_0= 'time_duration' ) ) otherlv_1= '(' ( (lv_value_2_0= RULE_INT ) ) otherlv_3= ')' )
                    // InternalDSLPolicies.g:665:4: ( (lv_pathtype_0_0= 'time_duration' ) ) otherlv_1= '(' ( (lv_value_2_0= RULE_INT ) ) otherlv_3= ')'
                    {
                    // InternalDSLPolicies.g:665:4: ( (lv_pathtype_0_0= 'time_duration' ) )
                    // InternalDSLPolicies.g:666:5: (lv_pathtype_0_0= 'time_duration' )
                    {
                    // InternalDSLPolicies.g:666:5: (lv_pathtype_0_0= 'time_duration' )
                    // InternalDSLPolicies.g:667:6: lv_pathtype_0_0= 'time_duration'
                    {
                    lv_pathtype_0_0=(Token)match(input,29,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_pathtype_0_0, grammarAccess.getIntegerStopConditionAccess().getPathtypeTime_durationKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getIntegerStopConditionRule());
                      						}
                      						setWithLastConsumed(current, "pathtype", lv_pathtype_0_0, "time_duration");
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,23,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getIntegerStopConditionAccess().getLeftParenthesisKeyword_0_1());
                      			
                    }
                    // InternalDSLPolicies.g:683:4: ( (lv_value_2_0= RULE_INT ) )
                    // InternalDSLPolicies.g:684:5: (lv_value_2_0= RULE_INT )
                    {
                    // InternalDSLPolicies.g:684:5: (lv_value_2_0= RULE_INT )
                    // InternalDSLPolicies.g:685:6: lv_value_2_0= RULE_INT
                    {
                    lv_value_2_0=(Token)match(input,RULE_INT,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_value_2_0, grammarAccess.getIntegerStopConditionAccess().getValueINTTerminalRuleCall_0_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getIntegerStopConditionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"value",
                      							lv_value_2_0,
                      							"org.eclipse.xtext.xbase.Xbase.INT");
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getIntegerStopConditionAccess().getRightParenthesisKeyword_0_3());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:707:3: ( ( (lv_pathtype_4_0= 'dependency_edge_coverage' ) ) otherlv_5= '(' ( (lv_value_6_0= RULE_INT ) ) otherlv_7= ')' )
                    {
                    // InternalDSLPolicies.g:707:3: ( ( (lv_pathtype_4_0= 'dependency_edge_coverage' ) ) otherlv_5= '(' ( (lv_value_6_0= RULE_INT ) ) otherlv_7= ')' )
                    // InternalDSLPolicies.g:708:4: ( (lv_pathtype_4_0= 'dependency_edge_coverage' ) ) otherlv_5= '(' ( (lv_value_6_0= RULE_INT ) ) otherlv_7= ')'
                    {
                    // InternalDSLPolicies.g:708:4: ( (lv_pathtype_4_0= 'dependency_edge_coverage' ) )
                    // InternalDSLPolicies.g:709:5: (lv_pathtype_4_0= 'dependency_edge_coverage' )
                    {
                    // InternalDSLPolicies.g:709:5: (lv_pathtype_4_0= 'dependency_edge_coverage' )
                    // InternalDSLPolicies.g:710:6: lv_pathtype_4_0= 'dependency_edge_coverage'
                    {
                    lv_pathtype_4_0=(Token)match(input,30,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_pathtype_4_0, grammarAccess.getIntegerStopConditionAccess().getPathtypeDependency_edge_coverageKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getIntegerStopConditionRule());
                      						}
                      						setWithLastConsumed(current, "pathtype", lv_pathtype_4_0, "dependency_edge_coverage");
                      					
                    }

                    }


                    }

                    otherlv_5=(Token)match(input,23,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getIntegerStopConditionAccess().getLeftParenthesisKeyword_1_1());
                      			
                    }
                    // InternalDSLPolicies.g:726:4: ( (lv_value_6_0= RULE_INT ) )
                    // InternalDSLPolicies.g:727:5: (lv_value_6_0= RULE_INT )
                    {
                    // InternalDSLPolicies.g:727:5: (lv_value_6_0= RULE_INT )
                    // InternalDSLPolicies.g:728:6: lv_value_6_0= RULE_INT
                    {
                    lv_value_6_0=(Token)match(input,RULE_INT,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_value_6_0, grammarAccess.getIntegerStopConditionAccess().getValueINTTerminalRuleCall_1_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getIntegerStopConditionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"value",
                      							lv_value_6_0,
                      							"org.eclipse.xtext.xbase.Xbase.INT");
                      					
                    }

                    }


                    }

                    otherlv_7=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIntegerStopConditionAccess().getRightParenthesisKeyword_1_3());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIntegerStopCondition"


    // $ANTLR start "entryRulePercentageCondition"
    // InternalDSLPolicies.g:753:1: entryRulePercentageCondition returns [EObject current=null] : iv_rulePercentageCondition= rulePercentageCondition EOF ;
    public final EObject entryRulePercentageCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePercentageCondition = null;


        try {
            // InternalDSLPolicies.g:753:60: (iv_rulePercentageCondition= rulePercentageCondition EOF )
            // InternalDSLPolicies.g:754:2: iv_rulePercentageCondition= rulePercentageCondition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPercentageConditionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePercentageCondition=rulePercentageCondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePercentageCondition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePercentageCondition"


    // $ANTLR start "rulePercentageCondition"
    // InternalDSLPolicies.g:760:1: rulePercentageCondition returns [EObject current=null] : ( ( ( (lv_pathtype_0_0= 'edge_coverage' ) ) otherlv_1= '(' ( (lv_percentage_2_0= rulePercent ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'vertex_coverage' ) ) otherlv_5= '(' ( (lv_percentage_6_0= rulePercent ) ) otherlv_7= ')' ) | ( ( (lv_pathtype_8_0= 'requirement_coverage' ) ) otherlv_9= '(' ( (lv_percentage_10_0= rulePercent ) ) otherlv_11= ')' ) ) ;
    public final EObject rulePercentageCondition() throws RecognitionException {
        EObject current = null;

        Token lv_pathtype_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token lv_pathtype_4_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token lv_pathtype_8_0=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_percentage_2_0 = null;

        AntlrDatatypeRuleToken lv_percentage_6_0 = null;

        AntlrDatatypeRuleToken lv_percentage_10_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:766:2: ( ( ( ( (lv_pathtype_0_0= 'edge_coverage' ) ) otherlv_1= '(' ( (lv_percentage_2_0= rulePercent ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'vertex_coverage' ) ) otherlv_5= '(' ( (lv_percentage_6_0= rulePercent ) ) otherlv_7= ')' ) | ( ( (lv_pathtype_8_0= 'requirement_coverage' ) ) otherlv_9= '(' ( (lv_percentage_10_0= rulePercent ) ) otherlv_11= ')' ) ) )
            // InternalDSLPolicies.g:767:2: ( ( ( (lv_pathtype_0_0= 'edge_coverage' ) ) otherlv_1= '(' ( (lv_percentage_2_0= rulePercent ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'vertex_coverage' ) ) otherlv_5= '(' ( (lv_percentage_6_0= rulePercent ) ) otherlv_7= ')' ) | ( ( (lv_pathtype_8_0= 'requirement_coverage' ) ) otherlv_9= '(' ( (lv_percentage_10_0= rulePercent ) ) otherlv_11= ')' ) )
            {
            // InternalDSLPolicies.g:767:2: ( ( ( (lv_pathtype_0_0= 'edge_coverage' ) ) otherlv_1= '(' ( (lv_percentage_2_0= rulePercent ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'vertex_coverage' ) ) otherlv_5= '(' ( (lv_percentage_6_0= rulePercent ) ) otherlv_7= ')' ) | ( ( (lv_pathtype_8_0= 'requirement_coverage' ) ) otherlv_9= '(' ( (lv_percentage_10_0= rulePercent ) ) otherlv_11= ')' ) )
            int alt15=3;
            switch ( input.LA(1) ) {
            case 31:
                {
                alt15=1;
                }
                break;
            case 32:
                {
                alt15=2;
                }
                break;
            case 33:
                {
                alt15=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // InternalDSLPolicies.g:768:3: ( ( (lv_pathtype_0_0= 'edge_coverage' ) ) otherlv_1= '(' ( (lv_percentage_2_0= rulePercent ) ) otherlv_3= ')' )
                    {
                    // InternalDSLPolicies.g:768:3: ( ( (lv_pathtype_0_0= 'edge_coverage' ) ) otherlv_1= '(' ( (lv_percentage_2_0= rulePercent ) ) otherlv_3= ')' )
                    // InternalDSLPolicies.g:769:4: ( (lv_pathtype_0_0= 'edge_coverage' ) ) otherlv_1= '(' ( (lv_percentage_2_0= rulePercent ) ) otherlv_3= ')'
                    {
                    // InternalDSLPolicies.g:769:4: ( (lv_pathtype_0_0= 'edge_coverage' ) )
                    // InternalDSLPolicies.g:770:5: (lv_pathtype_0_0= 'edge_coverage' )
                    {
                    // InternalDSLPolicies.g:770:5: (lv_pathtype_0_0= 'edge_coverage' )
                    // InternalDSLPolicies.g:771:6: lv_pathtype_0_0= 'edge_coverage'
                    {
                    lv_pathtype_0_0=(Token)match(input,31,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_pathtype_0_0, grammarAccess.getPercentageConditionAccess().getPathtypeEdge_coverageKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPercentageConditionRule());
                      						}
                      						setWithLastConsumed(current, "pathtype", lv_pathtype_0_0, "edge_coverage");
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,23,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getPercentageConditionAccess().getLeftParenthesisKeyword_0_1());
                      			
                    }
                    // InternalDSLPolicies.g:787:4: ( (lv_percentage_2_0= rulePercent ) )
                    // InternalDSLPolicies.g:788:5: (lv_percentage_2_0= rulePercent )
                    {
                    // InternalDSLPolicies.g:788:5: (lv_percentage_2_0= rulePercent )
                    // InternalDSLPolicies.g:789:6: lv_percentage_2_0= rulePercent
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPercentageConditionAccess().getPercentagePercentParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_percentage_2_0=rulePercent();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPercentageConditionRule());
                      						}
                      						set(
                      							current,
                      							"percentage",
                      							lv_percentage_2_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.Percent");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getPercentageConditionAccess().getRightParenthesisKeyword_0_3());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:812:3: ( ( (lv_pathtype_4_0= 'vertex_coverage' ) ) otherlv_5= '(' ( (lv_percentage_6_0= rulePercent ) ) otherlv_7= ')' )
                    {
                    // InternalDSLPolicies.g:812:3: ( ( (lv_pathtype_4_0= 'vertex_coverage' ) ) otherlv_5= '(' ( (lv_percentage_6_0= rulePercent ) ) otherlv_7= ')' )
                    // InternalDSLPolicies.g:813:4: ( (lv_pathtype_4_0= 'vertex_coverage' ) ) otherlv_5= '(' ( (lv_percentage_6_0= rulePercent ) ) otherlv_7= ')'
                    {
                    // InternalDSLPolicies.g:813:4: ( (lv_pathtype_4_0= 'vertex_coverage' ) )
                    // InternalDSLPolicies.g:814:5: (lv_pathtype_4_0= 'vertex_coverage' )
                    {
                    // InternalDSLPolicies.g:814:5: (lv_pathtype_4_0= 'vertex_coverage' )
                    // InternalDSLPolicies.g:815:6: lv_pathtype_4_0= 'vertex_coverage'
                    {
                    lv_pathtype_4_0=(Token)match(input,32,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_pathtype_4_0, grammarAccess.getPercentageConditionAccess().getPathtypeVertex_coverageKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPercentageConditionRule());
                      						}
                      						setWithLastConsumed(current, "pathtype", lv_pathtype_4_0, "vertex_coverage");
                      					
                    }

                    }


                    }

                    otherlv_5=(Token)match(input,23,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getPercentageConditionAccess().getLeftParenthesisKeyword_1_1());
                      			
                    }
                    // InternalDSLPolicies.g:831:4: ( (lv_percentage_6_0= rulePercent ) )
                    // InternalDSLPolicies.g:832:5: (lv_percentage_6_0= rulePercent )
                    {
                    // InternalDSLPolicies.g:832:5: (lv_percentage_6_0= rulePercent )
                    // InternalDSLPolicies.g:833:6: lv_percentage_6_0= rulePercent
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPercentageConditionAccess().getPercentagePercentParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_percentage_6_0=rulePercent();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPercentageConditionRule());
                      						}
                      						set(
                      							current,
                      							"percentage",
                      							lv_percentage_6_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.Percent");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_7=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getPercentageConditionAccess().getRightParenthesisKeyword_1_3());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:856:3: ( ( (lv_pathtype_8_0= 'requirement_coverage' ) ) otherlv_9= '(' ( (lv_percentage_10_0= rulePercent ) ) otherlv_11= ')' )
                    {
                    // InternalDSLPolicies.g:856:3: ( ( (lv_pathtype_8_0= 'requirement_coverage' ) ) otherlv_9= '(' ( (lv_percentage_10_0= rulePercent ) ) otherlv_11= ')' )
                    // InternalDSLPolicies.g:857:4: ( (lv_pathtype_8_0= 'requirement_coverage' ) ) otherlv_9= '(' ( (lv_percentage_10_0= rulePercent ) ) otherlv_11= ')'
                    {
                    // InternalDSLPolicies.g:857:4: ( (lv_pathtype_8_0= 'requirement_coverage' ) )
                    // InternalDSLPolicies.g:858:5: (lv_pathtype_8_0= 'requirement_coverage' )
                    {
                    // InternalDSLPolicies.g:858:5: (lv_pathtype_8_0= 'requirement_coverage' )
                    // InternalDSLPolicies.g:859:6: lv_pathtype_8_0= 'requirement_coverage'
                    {
                    lv_pathtype_8_0=(Token)match(input,33,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_pathtype_8_0, grammarAccess.getPercentageConditionAccess().getPathtypeRequirement_coverageKeyword_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPercentageConditionRule());
                      						}
                      						setWithLastConsumed(current, "pathtype", lv_pathtype_8_0, "requirement_coverage");
                      					
                    }

                    }


                    }

                    otherlv_9=(Token)match(input,23,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getPercentageConditionAccess().getLeftParenthesisKeyword_2_1());
                      			
                    }
                    // InternalDSLPolicies.g:875:4: ( (lv_percentage_10_0= rulePercent ) )
                    // InternalDSLPolicies.g:876:5: (lv_percentage_10_0= rulePercent )
                    {
                    // InternalDSLPolicies.g:876:5: (lv_percentage_10_0= rulePercent )
                    // InternalDSLPolicies.g:877:6: lv_percentage_10_0= rulePercent
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPercentageConditionAccess().getPercentagePercentParserRuleCall_2_2_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_percentage_10_0=rulePercent();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPercentageConditionRule());
                      						}
                      						set(
                      							current,
                      							"percentage",
                      							lv_percentage_10_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.Percent");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_11=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_11, grammarAccess.getPercentageConditionAccess().getRightParenthesisKeyword_2_3());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePercentageCondition"


    // $ANTLR start "entryRuleStringCondition"
    // InternalDSLPolicies.g:903:1: entryRuleStringCondition returns [EObject current=null] : iv_ruleStringCondition= ruleStringCondition EOF ;
    public final EObject entryRuleStringCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringCondition = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");

        try {
            // InternalDSLPolicies.g:905:2: (iv_ruleStringCondition= ruleStringCondition EOF )
            // InternalDSLPolicies.g:906:2: iv_ruleStringCondition= ruleStringCondition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStringConditionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStringCondition=ruleStringCondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStringCondition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRuleStringCondition"


    // $ANTLR start "ruleStringCondition"
    // InternalDSLPolicies.g:915:1: ruleStringCondition returns [EObject current=null] : ( ( ( (lv_pathtype_0_0= 'reached_vertex' ) ) otherlv_1= '(' ( (lv_graphelement_2_0= ruleGraphElement ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'reached_edge' ) ) otherlv_5= '(' ( (lv_graphelement_6_0= ruleGraphElement ) ) otherlv_7= ')' ) ) ;
    public final EObject ruleStringCondition() throws RecognitionException {
        EObject current = null;

        Token lv_pathtype_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token lv_pathtype_4_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_graphelement_2_0 = null;

        EObject lv_graphelement_6_0 = null;



        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");

        try {
            // InternalDSLPolicies.g:922:2: ( ( ( ( (lv_pathtype_0_0= 'reached_vertex' ) ) otherlv_1= '(' ( (lv_graphelement_2_0= ruleGraphElement ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'reached_edge' ) ) otherlv_5= '(' ( (lv_graphelement_6_0= ruleGraphElement ) ) otherlv_7= ')' ) ) )
            // InternalDSLPolicies.g:923:2: ( ( ( (lv_pathtype_0_0= 'reached_vertex' ) ) otherlv_1= '(' ( (lv_graphelement_2_0= ruleGraphElement ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'reached_edge' ) ) otherlv_5= '(' ( (lv_graphelement_6_0= ruleGraphElement ) ) otherlv_7= ')' ) )
            {
            // InternalDSLPolicies.g:923:2: ( ( ( (lv_pathtype_0_0= 'reached_vertex' ) ) otherlv_1= '(' ( (lv_graphelement_2_0= ruleGraphElement ) ) otherlv_3= ')' ) | ( ( (lv_pathtype_4_0= 'reached_edge' ) ) otherlv_5= '(' ( (lv_graphelement_6_0= ruleGraphElement ) ) otherlv_7= ')' ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==34) ) {
                alt16=1;
            }
            else if ( (LA16_0==35) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalDSLPolicies.g:924:3: ( ( (lv_pathtype_0_0= 'reached_vertex' ) ) otherlv_1= '(' ( (lv_graphelement_2_0= ruleGraphElement ) ) otherlv_3= ')' )
                    {
                    // InternalDSLPolicies.g:924:3: ( ( (lv_pathtype_0_0= 'reached_vertex' ) ) otherlv_1= '(' ( (lv_graphelement_2_0= ruleGraphElement ) ) otherlv_3= ')' )
                    // InternalDSLPolicies.g:925:4: ( (lv_pathtype_0_0= 'reached_vertex' ) ) otherlv_1= '(' ( (lv_graphelement_2_0= ruleGraphElement ) ) otherlv_3= ')'
                    {
                    // InternalDSLPolicies.g:925:4: ( (lv_pathtype_0_0= 'reached_vertex' ) )
                    // InternalDSLPolicies.g:926:5: (lv_pathtype_0_0= 'reached_vertex' )
                    {
                    // InternalDSLPolicies.g:926:5: (lv_pathtype_0_0= 'reached_vertex' )
                    // InternalDSLPolicies.g:927:6: lv_pathtype_0_0= 'reached_vertex'
                    {
                    lv_pathtype_0_0=(Token)match(input,34,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_pathtype_0_0, grammarAccess.getStringConditionAccess().getPathtypeReached_vertexKeyword_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getStringConditionRule());
                      						}
                      						setWithLastConsumed(current, "pathtype", lv_pathtype_0_0, "reached_vertex");
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,23,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getStringConditionAccess().getLeftParenthesisKeyword_0_1());
                      			
                    }
                    // InternalDSLPolicies.g:943:4: ( (lv_graphelement_2_0= ruleGraphElement ) )
                    // InternalDSLPolicies.g:944:5: (lv_graphelement_2_0= ruleGraphElement )
                    {
                    // InternalDSLPolicies.g:944:5: (lv_graphelement_2_0= ruleGraphElement )
                    // InternalDSLPolicies.g:945:6: lv_graphelement_2_0= ruleGraphElement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStringConditionAccess().getGraphelementGraphElementParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_graphelement_2_0=ruleGraphElement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStringConditionRule());
                      						}
                      						set(
                      							current,
                      							"graphelement",
                      							lv_graphelement_2_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.GraphElement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getStringConditionAccess().getRightParenthesisKeyword_0_3());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:968:3: ( ( (lv_pathtype_4_0= 'reached_edge' ) ) otherlv_5= '(' ( (lv_graphelement_6_0= ruleGraphElement ) ) otherlv_7= ')' )
                    {
                    // InternalDSLPolicies.g:968:3: ( ( (lv_pathtype_4_0= 'reached_edge' ) ) otherlv_5= '(' ( (lv_graphelement_6_0= ruleGraphElement ) ) otherlv_7= ')' )
                    // InternalDSLPolicies.g:969:4: ( (lv_pathtype_4_0= 'reached_edge' ) ) otherlv_5= '(' ( (lv_graphelement_6_0= ruleGraphElement ) ) otherlv_7= ')'
                    {
                    // InternalDSLPolicies.g:969:4: ( (lv_pathtype_4_0= 'reached_edge' ) )
                    // InternalDSLPolicies.g:970:5: (lv_pathtype_4_0= 'reached_edge' )
                    {
                    // InternalDSLPolicies.g:970:5: (lv_pathtype_4_0= 'reached_edge' )
                    // InternalDSLPolicies.g:971:6: lv_pathtype_4_0= 'reached_edge'
                    {
                    lv_pathtype_4_0=(Token)match(input,35,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_pathtype_4_0, grammarAccess.getStringConditionAccess().getPathtypeReached_edgeKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getStringConditionRule());
                      						}
                      						setWithLastConsumed(current, "pathtype", lv_pathtype_4_0, "reached_edge");
                      					
                    }

                    }


                    }

                    otherlv_5=(Token)match(input,23,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getStringConditionAccess().getLeftParenthesisKeyword_1_1());
                      			
                    }
                    // InternalDSLPolicies.g:987:4: ( (lv_graphelement_6_0= ruleGraphElement ) )
                    // InternalDSLPolicies.g:988:5: (lv_graphelement_6_0= ruleGraphElement )
                    {
                    // InternalDSLPolicies.g:988:5: (lv_graphelement_6_0= ruleGraphElement )
                    // InternalDSLPolicies.g:989:6: lv_graphelement_6_0= ruleGraphElement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStringConditionAccess().getGraphelementGraphElementParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
                    lv_graphelement_6_0=ruleGraphElement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStringConditionRule());
                      						}
                      						set(
                      							current,
                      							"graphelement",
                      							lv_graphelement_6_0,
                      							"gw4e.eclipse.dsl.DSLPolicies.GraphElement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_7=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getStringConditionAccess().getRightParenthesisKeyword_1_3());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "ruleStringCondition"


    // $ANTLR start "entryRulePercent"
    // InternalDSLPolicies.g:1018:1: entryRulePercent returns [String current=null] : iv_rulePercent= rulePercent EOF ;
    public final String entryRulePercent() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePercent = null;


        try {
            // InternalDSLPolicies.g:1018:47: (iv_rulePercent= rulePercent EOF )
            // InternalDSLPolicies.g:1019:2: iv_rulePercent= rulePercent EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPercentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePercent=rulePercent();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePercent.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePercent"


    // $ANTLR start "rulePercent"
    // InternalDSLPolicies.g:1025:1: rulePercent returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_INT_0= RULE_INT ;
    public final AntlrDatatypeRuleToken rulePercent() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:1031:2: (this_INT_0= RULE_INT )
            // InternalDSLPolicies.g:1032:2: this_INT_0= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_INT_0);
              	
            }
            if ( state.backtracking==0 ) {

              		newLeafNode(this_INT_0, grammarAccess.getPercentAccess().getINTTerminalRuleCall());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePercent"


    // $ANTLR start "entryRuleGraphElement"
    // InternalDSLPolicies.g:1042:1: entryRuleGraphElement returns [EObject current=null] : iv_ruleGraphElement= ruleGraphElement EOF ;
    public final EObject entryRuleGraphElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGraphElement = null;


        try {
            // InternalDSLPolicies.g:1042:53: (iv_ruleGraphElement= ruleGraphElement EOF )
            // InternalDSLPolicies.g:1043:2: iv_ruleGraphElement= ruleGraphElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getGraphElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleGraphElement=ruleGraphElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleGraphElement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGraphElement"


    // $ANTLR start "ruleGraphElement"
    // InternalDSLPolicies.g:1049:1: ruleGraphElement returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleGraphElement() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:1055:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalDSLPolicies.g:1056:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalDSLPolicies.g:1056:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalDSLPolicies.g:1057:3: (lv_name_0_0= RULE_ID )
            {
            // InternalDSLPolicies.g:1057:3: (lv_name_0_0= RULE_ID )
            // InternalDSLPolicies.g:1058:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_name_0_0, grammarAccess.getGraphElementAccess().getNameIDTerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getGraphElementRule());
              				}
              				setWithLastConsumed(
              					current,
              					"name",
              					lv_name_0_0,
              					"org.eclipse.xtext.xbase.Xtype.ID");
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGraphElement"


    // $ANTLR start "entryRuleXExpression"
    // InternalDSLPolicies.g:1077:1: entryRuleXExpression returns [EObject current=null] : iv_ruleXExpression= ruleXExpression EOF ;
    public final EObject entryRuleXExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXExpression = null;


        try {
            // InternalDSLPolicies.g:1077:52: (iv_ruleXExpression= ruleXExpression EOF )
            // InternalDSLPolicies.g:1078:2: iv_ruleXExpression= ruleXExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXExpression=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXExpression"


    // $ANTLR start "ruleXExpression"
    // InternalDSLPolicies.g:1084:1: ruleXExpression returns [EObject current=null] : this_XAssignment_0= ruleXAssignment ;
    public final EObject ruleXExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XAssignment_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:1090:2: (this_XAssignment_0= ruleXAssignment )
            // InternalDSLPolicies.g:1091:2: this_XAssignment_0= ruleXAssignment
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getXExpressionAccess().getXAssignmentParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_XAssignment_0=ruleXAssignment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current = this_XAssignment_0;
              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXExpression"


    // $ANTLR start "entryRuleXAssignment"
    // InternalDSLPolicies.g:1102:1: entryRuleXAssignment returns [EObject current=null] : iv_ruleXAssignment= ruleXAssignment EOF ;
    public final EObject entryRuleXAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXAssignment = null;


        try {
            // InternalDSLPolicies.g:1102:52: (iv_ruleXAssignment= ruleXAssignment EOF )
            // InternalDSLPolicies.g:1103:2: iv_ruleXAssignment= ruleXAssignment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXAssignmentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXAssignment=ruleXAssignment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXAssignment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXAssignment"


    // $ANTLR start "ruleXAssignment"
    // InternalDSLPolicies.g:1109:1: ruleXAssignment returns [EObject current=null] : ( ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) ) | (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? ) ) ;
    public final EObject ruleXAssignment() throws RecognitionException {
        EObject current = null;

        EObject lv_value_3_0 = null;

        EObject this_XOrExpression_4 = null;

        EObject lv_rightOperand_7_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:1115:2: ( ( ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) ) | (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? ) ) )
            // InternalDSLPolicies.g:1116:2: ( ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) ) | (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? ) )
            {
            // InternalDSLPolicies.g:1116:2: ( ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) ) | (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? ) )
            int alt18=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA18_1 = input.LA(2);

                if ( (LA18_1==16) ) {
                    alt18=1;
                }
                else if ( (LA18_1==EOF||LA18_1==RULE_ID||(LA18_1>=RULE_INT && LA18_1<=RULE_DECIMAL)||(LA18_1>=22 && LA18_1<=24)||(LA18_1>=36 && LA18_1<=75)||(LA18_1>=77 && LA18_1<=103)) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }
                }
                break;
            case 88:
                {
                int LA18_2 = input.LA(2);

                if ( (LA18_2==EOF||LA18_2==RULE_ID||(LA18_2>=RULE_INT && LA18_2<=RULE_DECIMAL)||(LA18_2>=22 && LA18_2<=24)||(LA18_2>=36 && LA18_2<=75)||(LA18_2>=77 && LA18_2<=103)) ) {
                    alt18=2;
                }
                else if ( (LA18_2==16) ) {
                    alt18=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 2, input);

                    throw nvae;
                }
                }
                break;
            case 89:
                {
                int LA18_3 = input.LA(2);

                if ( (LA18_3==EOF||LA18_3==RULE_ID||(LA18_3>=RULE_INT && LA18_3<=RULE_DECIMAL)||(LA18_3>=22 && LA18_3<=24)||(LA18_3>=36 && LA18_3<=75)||(LA18_3>=77 && LA18_3<=103)) ) {
                    alt18=2;
                }
                else if ( (LA18_3==16) ) {
                    alt18=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 3, input);

                    throw nvae;
                }
                }
                break;
            case 90:
                {
                int LA18_4 = input.LA(2);

                if ( (LA18_4==EOF||LA18_4==RULE_ID||(LA18_4>=RULE_INT && LA18_4<=RULE_DECIMAL)||(LA18_4>=22 && LA18_4<=24)||(LA18_4>=36 && LA18_4<=75)||(LA18_4>=77 && LA18_4<=103)) ) {
                    alt18=2;
                }
                else if ( (LA18_4==16) ) {
                    alt18=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 4, input);

                    throw nvae;
                }
                }
                break;
            case 91:
                {
                int LA18_5 = input.LA(2);

                if ( (LA18_5==16) ) {
                    alt18=1;
                }
                else if ( (LA18_5==EOF||LA18_5==RULE_ID||(LA18_5>=RULE_INT && LA18_5<=RULE_DECIMAL)||(LA18_5>=22 && LA18_5<=24)||(LA18_5>=36 && LA18_5<=75)||(LA18_5>=77 && LA18_5<=103)) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 5, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
            case RULE_STRING:
            case RULE_HEX:
            case RULE_DECIMAL:
            case 23:
            case 41:
            case 57:
            case 58:
            case 63:
            case 71:
            case 72:
            case 74:
            case 77:
            case 79:
            case 83:
            case 84:
            case 85:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 102:
                {
                alt18=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalDSLPolicies.g:1117:3: ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) )
                    {
                    // InternalDSLPolicies.g:1117:3: ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) )
                    // InternalDSLPolicies.g:1118:4: () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) )
                    {
                    // InternalDSLPolicies.g:1118:4: ()
                    // InternalDSLPolicies.g:1119:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getXAssignmentAccess().getXAssignmentAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalDSLPolicies.g:1125:4: ( ( ruleFeatureCallID ) )
                    // InternalDSLPolicies.g:1126:5: ( ruleFeatureCallID )
                    {
                    // InternalDSLPolicies.g:1126:5: ( ruleFeatureCallID )
                    // InternalDSLPolicies.g:1127:6: ruleFeatureCallID
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getXAssignmentRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXAssignmentAccess().getFeatureJvmIdentifiableElementCrossReference_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_7);
                    ruleFeatureCallID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getXAssignmentAccess().getOpSingleAssignParserRuleCall_0_2());
                      			
                    }
                    pushFollow(FOLLOW_21);
                    ruleOpSingleAssign();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    // InternalDSLPolicies.g:1148:4: ( (lv_value_3_0= ruleXAssignment ) )
                    // InternalDSLPolicies.g:1149:5: (lv_value_3_0= ruleXAssignment )
                    {
                    // InternalDSLPolicies.g:1149:5: (lv_value_3_0= ruleXAssignment )
                    // InternalDSLPolicies.g:1150:6: lv_value_3_0= ruleXAssignment
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXAssignmentAccess().getValueXAssignmentParserRuleCall_0_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_3_0=ruleXAssignment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXAssignmentRule());
                      						}
                      						set(
                      							current,
                      							"value",
                      							lv_value_3_0,
                      							"org.eclipse.xtext.xbase.Xbase.XAssignment");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:1169:3: (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? )
                    {
                    // InternalDSLPolicies.g:1169:3: (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? )
                    // InternalDSLPolicies.g:1170:4: this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )?
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getXAssignmentAccess().getXOrExpressionParserRuleCall_1_0());
                      			
                    }
                    pushFollow(FOLLOW_22);
                    this_XOrExpression_4=ruleXOrExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_XOrExpression_4;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    // InternalDSLPolicies.g:1178:4: ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )?
                    int alt17=2;
                    alt17 = dfa17.predict(input);
                    switch (alt17) {
                        case 1 :
                            // InternalDSLPolicies.g:1179:5: ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) )
                            {
                            // InternalDSLPolicies.g:1179:5: ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) )
                            // InternalDSLPolicies.g:1180:6: ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) )
                            {
                            // InternalDSLPolicies.g:1190:6: ( () ( ( ruleOpMultiAssign ) ) )
                            // InternalDSLPolicies.g:1191:7: () ( ( ruleOpMultiAssign ) )
                            {
                            // InternalDSLPolicies.g:1191:7: ()
                            // InternalDSLPolicies.g:1192:8: 
                            {
                            if ( state.backtracking==0 ) {

                              								current = forceCreateModelElementAndSet(
                              									grammarAccess.getXAssignmentAccess().getXBinaryOperationLeftOperandAction_1_1_0_0_0(),
                              									current);
                              							
                            }

                            }

                            // InternalDSLPolicies.g:1198:7: ( ( ruleOpMultiAssign ) )
                            // InternalDSLPolicies.g:1199:8: ( ruleOpMultiAssign )
                            {
                            // InternalDSLPolicies.g:1199:8: ( ruleOpMultiAssign )
                            // InternalDSLPolicies.g:1200:9: ruleOpMultiAssign
                            {
                            if ( state.backtracking==0 ) {

                              									if (current==null) {
                              										current = createModelElement(grammarAccess.getXAssignmentRule());
                              									}
                              								
                            }
                            if ( state.backtracking==0 ) {

                              									newCompositeNode(grammarAccess.getXAssignmentAccess().getFeatureJvmIdentifiableElementCrossReference_1_1_0_0_1_0());
                              								
                            }
                            pushFollow(FOLLOW_21);
                            ruleOpMultiAssign();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              									afterParserOrEnumRuleCall();
                              								
                            }

                            }


                            }


                            }


                            }

                            // InternalDSLPolicies.g:1216:5: ( (lv_rightOperand_7_0= ruleXAssignment ) )
                            // InternalDSLPolicies.g:1217:6: (lv_rightOperand_7_0= ruleXAssignment )
                            {
                            // InternalDSLPolicies.g:1217:6: (lv_rightOperand_7_0= ruleXAssignment )
                            // InternalDSLPolicies.g:1218:7: lv_rightOperand_7_0= ruleXAssignment
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getXAssignmentAccess().getRightOperandXAssignmentParserRuleCall_1_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_rightOperand_7_0=ruleXAssignment();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getXAssignmentRule());
                              							}
                              							set(
                              								current,
                              								"rightOperand",
                              								lv_rightOperand_7_0,
                              								"org.eclipse.xtext.xbase.Xbase.XAssignment");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXAssignment"


    // $ANTLR start "entryRuleOpSingleAssign"
    // InternalDSLPolicies.g:1241:1: entryRuleOpSingleAssign returns [String current=null] : iv_ruleOpSingleAssign= ruleOpSingleAssign EOF ;
    public final String entryRuleOpSingleAssign() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpSingleAssign = null;


        try {
            // InternalDSLPolicies.g:1241:54: (iv_ruleOpSingleAssign= ruleOpSingleAssign EOF )
            // InternalDSLPolicies.g:1242:2: iv_ruleOpSingleAssign= ruleOpSingleAssign EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpSingleAssignRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpSingleAssign=ruleOpSingleAssign();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpSingleAssign.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpSingleAssign"


    // $ANTLR start "ruleOpSingleAssign"
    // InternalDSLPolicies.g:1248:1: ruleOpSingleAssign returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '=' ;
    public final AntlrDatatypeRuleToken ruleOpSingleAssign() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:1254:2: (kw= '=' )
            // InternalDSLPolicies.g:1255:2: kw= '='
            {
            kw=(Token)match(input,16,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.getOpSingleAssignAccess().getEqualsSignKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpSingleAssign"


    // $ANTLR start "entryRuleOpMultiAssign"
    // InternalDSLPolicies.g:1263:1: entryRuleOpMultiAssign returns [String current=null] : iv_ruleOpMultiAssign= ruleOpMultiAssign EOF ;
    public final String entryRuleOpMultiAssign() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpMultiAssign = null;


        try {
            // InternalDSLPolicies.g:1263:53: (iv_ruleOpMultiAssign= ruleOpMultiAssign EOF )
            // InternalDSLPolicies.g:1264:2: iv_ruleOpMultiAssign= ruleOpMultiAssign EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpMultiAssignRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpMultiAssign=ruleOpMultiAssign();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpMultiAssign.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpMultiAssign"


    // $ANTLR start "ruleOpMultiAssign"
    // InternalDSLPolicies.g:1270:1: ruleOpMultiAssign returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '+=' | kw= '-=' | kw= '*=' | kw= '/=' | kw= '%=' | (kw= '<' kw= '<' kw= '=' ) | (kw= '>' (kw= '>' )? kw= '>=' ) ) ;
    public final AntlrDatatypeRuleToken ruleOpMultiAssign() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:1276:2: ( (kw= '+=' | kw= '-=' | kw= '*=' | kw= '/=' | kw= '%=' | (kw= '<' kw= '<' kw= '=' ) | (kw= '>' (kw= '>' )? kw= '>=' ) ) )
            // InternalDSLPolicies.g:1277:2: (kw= '+=' | kw= '-=' | kw= '*=' | kw= '/=' | kw= '%=' | (kw= '<' kw= '<' kw= '=' ) | (kw= '>' (kw= '>' )? kw= '>=' ) )
            {
            // InternalDSLPolicies.g:1277:2: (kw= '+=' | kw= '-=' | kw= '*=' | kw= '/=' | kw= '%=' | (kw= '<' kw= '<' kw= '=' ) | (kw= '>' (kw= '>' )? kw= '>=' ) )
            int alt20=7;
            switch ( input.LA(1) ) {
            case 36:
                {
                alt20=1;
                }
                break;
            case 37:
                {
                alt20=2;
                }
                break;
            case 38:
                {
                alt20=3;
                }
                break;
            case 39:
                {
                alt20=4;
                }
                break;
            case 40:
                {
                alt20=5;
                }
                break;
            case 41:
                {
                alt20=6;
                }
                break;
            case 42:
                {
                alt20=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // InternalDSLPolicies.g:1278:3: kw= '+='
                    {
                    kw=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getPlusSignEqualsSignKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:1284:3: kw= '-='
                    {
                    kw=(Token)match(input,37,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getHyphenMinusEqualsSignKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:1290:3: kw= '*='
                    {
                    kw=(Token)match(input,38,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getAsteriskEqualsSignKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalDSLPolicies.g:1296:3: kw= '/='
                    {
                    kw=(Token)match(input,39,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getSolidusEqualsSignKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalDSLPolicies.g:1302:3: kw= '%='
                    {
                    kw=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getPercentSignEqualsSignKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalDSLPolicies.g:1308:3: (kw= '<' kw= '<' kw= '=' )
                    {
                    // InternalDSLPolicies.g:1308:3: (kw= '<' kw= '<' kw= '=' )
                    // InternalDSLPolicies.g:1309:4: kw= '<' kw= '<' kw= '='
                    {
                    kw=(Token)match(input,41,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getLessThanSignKeyword_5_0());
                      			
                    }
                    kw=(Token)match(input,41,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getLessThanSignKeyword_5_1());
                      			
                    }
                    kw=(Token)match(input,16,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getEqualsSignKeyword_5_2());
                      			
                    }

                    }


                    }
                    break;
                case 7 :
                    // InternalDSLPolicies.g:1326:3: (kw= '>' (kw= '>' )? kw= '>=' )
                    {
                    // InternalDSLPolicies.g:1326:3: (kw= '>' (kw= '>' )? kw= '>=' )
                    // InternalDSLPolicies.g:1327:4: kw= '>' (kw= '>' )? kw= '>='
                    {
                    kw=(Token)match(input,42,FOLLOW_24); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getGreaterThanSignKeyword_6_0());
                      			
                    }
                    // InternalDSLPolicies.g:1332:4: (kw= '>' )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==42) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalDSLPolicies.g:1333:5: kw= '>'
                            {
                            kw=(Token)match(input,42,FOLLOW_25); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getGreaterThanSignKeyword_6_1());
                              				
                            }

                            }
                            break;

                    }

                    kw=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getGreaterThanSignEqualsSignKeyword_6_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpMultiAssign"


    // $ANTLR start "entryRuleXOrExpression"
    // InternalDSLPolicies.g:1349:1: entryRuleXOrExpression returns [EObject current=null] : iv_ruleXOrExpression= ruleXOrExpression EOF ;
    public final EObject entryRuleXOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXOrExpression = null;


        try {
            // InternalDSLPolicies.g:1349:54: (iv_ruleXOrExpression= ruleXOrExpression EOF )
            // InternalDSLPolicies.g:1350:2: iv_ruleXOrExpression= ruleXOrExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXOrExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXOrExpression=ruleXOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXOrExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXOrExpression"


    // $ANTLR start "ruleXOrExpression"
    // InternalDSLPolicies.g:1356:1: ruleXOrExpression returns [EObject current=null] : (this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )* ) ;
    public final EObject ruleXOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XAndExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:1362:2: ( (this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )* ) )
            // InternalDSLPolicies.g:1363:2: (this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )* )
            {
            // InternalDSLPolicies.g:1363:2: (this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )* )
            // InternalDSLPolicies.g:1364:3: this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXOrExpressionAccess().getXAndExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_26);
            this_XAndExpression_0=ruleXAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XAndExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:1372:3: ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==44) ) {
                    int LA21_2 = input.LA(2);

                    if ( (synpred2_InternalDSLPolicies()) ) {
                        alt21=1;
                    }


                }


                switch (alt21) {
            	case 1 :
            	    // InternalDSLPolicies.g:1373:4: ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) )
            	    {
            	    // InternalDSLPolicies.g:1373:4: ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) )
            	    // InternalDSLPolicies.g:1374:5: ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) )
            	    {
            	    // InternalDSLPolicies.g:1384:5: ( () ( ( ruleOpOr ) ) )
            	    // InternalDSLPolicies.g:1385:6: () ( ( ruleOpOr ) )
            	    {
            	    // InternalDSLPolicies.g:1385:6: ()
            	    // InternalDSLPolicies.g:1386:7: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      							current = forceCreateModelElementAndSet(
            	      								grammarAccess.getXOrExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
            	      								current);
            	      						
            	    }

            	    }

            	    // InternalDSLPolicies.g:1392:6: ( ( ruleOpOr ) )
            	    // InternalDSLPolicies.g:1393:7: ( ruleOpOr )
            	    {
            	    // InternalDSLPolicies.g:1393:7: ( ruleOpOr )
            	    // InternalDSLPolicies.g:1394:8: ruleOpOr
            	    {
            	    if ( state.backtracking==0 ) {

            	      								if (current==null) {
            	      									current = createModelElement(grammarAccess.getXOrExpressionRule());
            	      								}
            	      							
            	    }
            	    if ( state.backtracking==0 ) {

            	      								newCompositeNode(grammarAccess.getXOrExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
            	      							
            	    }
            	    pushFollow(FOLLOW_21);
            	    ruleOpOr();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								afterParserOrEnumRuleCall();
            	      							
            	    }

            	    }


            	    }


            	    }


            	    }

            	    // InternalDSLPolicies.g:1410:4: ( (lv_rightOperand_3_0= ruleXAndExpression ) )
            	    // InternalDSLPolicies.g:1411:5: (lv_rightOperand_3_0= ruleXAndExpression )
            	    {
            	    // InternalDSLPolicies.g:1411:5: (lv_rightOperand_3_0= ruleXAndExpression )
            	    // InternalDSLPolicies.g:1412:6: lv_rightOperand_3_0= ruleXAndExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getXOrExpressionAccess().getRightOperandXAndExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_26);
            	    lv_rightOperand_3_0=ruleXAndExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getXOrExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"rightOperand",
            	      							lv_rightOperand_3_0,
            	      							"org.eclipse.xtext.xbase.Xbase.XAndExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXOrExpression"


    // $ANTLR start "entryRuleOpOr"
    // InternalDSLPolicies.g:1434:1: entryRuleOpOr returns [String current=null] : iv_ruleOpOr= ruleOpOr EOF ;
    public final String entryRuleOpOr() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpOr = null;


        try {
            // InternalDSLPolicies.g:1434:44: (iv_ruleOpOr= ruleOpOr EOF )
            // InternalDSLPolicies.g:1435:2: iv_ruleOpOr= ruleOpOr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpOrRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpOr=ruleOpOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpOr.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpOr"


    // $ANTLR start "ruleOpOr"
    // InternalDSLPolicies.g:1441:1: ruleOpOr returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '||' ;
    public final AntlrDatatypeRuleToken ruleOpOr() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:1447:2: (kw= '||' )
            // InternalDSLPolicies.g:1448:2: kw= '||'
            {
            kw=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.getOpOrAccess().getVerticalLineVerticalLineKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpOr"


    // $ANTLR start "entryRuleXAndExpression"
    // InternalDSLPolicies.g:1456:1: entryRuleXAndExpression returns [EObject current=null] : iv_ruleXAndExpression= ruleXAndExpression EOF ;
    public final EObject entryRuleXAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXAndExpression = null;


        try {
            // InternalDSLPolicies.g:1456:55: (iv_ruleXAndExpression= ruleXAndExpression EOF )
            // InternalDSLPolicies.g:1457:2: iv_ruleXAndExpression= ruleXAndExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXAndExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXAndExpression=ruleXAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXAndExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXAndExpression"


    // $ANTLR start "ruleXAndExpression"
    // InternalDSLPolicies.g:1463:1: ruleXAndExpression returns [EObject current=null] : (this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )* ) ;
    public final EObject ruleXAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XEqualityExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:1469:2: ( (this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )* ) )
            // InternalDSLPolicies.g:1470:2: (this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )* )
            {
            // InternalDSLPolicies.g:1470:2: (this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )* )
            // InternalDSLPolicies.g:1471:3: this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXAndExpressionAccess().getXEqualityExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_27);
            this_XEqualityExpression_0=ruleXEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XEqualityExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:1479:3: ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==45) ) {
                    int LA22_2 = input.LA(2);

                    if ( (synpred3_InternalDSLPolicies()) ) {
                        alt22=1;
                    }


                }


                switch (alt22) {
            	case 1 :
            	    // InternalDSLPolicies.g:1480:4: ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) )
            	    {
            	    // InternalDSLPolicies.g:1480:4: ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) )
            	    // InternalDSLPolicies.g:1481:5: ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) )
            	    {
            	    // InternalDSLPolicies.g:1491:5: ( () ( ( ruleOpAnd ) ) )
            	    // InternalDSLPolicies.g:1492:6: () ( ( ruleOpAnd ) )
            	    {
            	    // InternalDSLPolicies.g:1492:6: ()
            	    // InternalDSLPolicies.g:1493:7: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      							current = forceCreateModelElementAndSet(
            	      								grammarAccess.getXAndExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
            	      								current);
            	      						
            	    }

            	    }

            	    // InternalDSLPolicies.g:1499:6: ( ( ruleOpAnd ) )
            	    // InternalDSLPolicies.g:1500:7: ( ruleOpAnd )
            	    {
            	    // InternalDSLPolicies.g:1500:7: ( ruleOpAnd )
            	    // InternalDSLPolicies.g:1501:8: ruleOpAnd
            	    {
            	    if ( state.backtracking==0 ) {

            	      								if (current==null) {
            	      									current = createModelElement(grammarAccess.getXAndExpressionRule());
            	      								}
            	      							
            	    }
            	    if ( state.backtracking==0 ) {

            	      								newCompositeNode(grammarAccess.getXAndExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
            	      							
            	    }
            	    pushFollow(FOLLOW_21);
            	    ruleOpAnd();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								afterParserOrEnumRuleCall();
            	      							
            	    }

            	    }


            	    }


            	    }


            	    }

            	    // InternalDSLPolicies.g:1517:4: ( (lv_rightOperand_3_0= ruleXEqualityExpression ) )
            	    // InternalDSLPolicies.g:1518:5: (lv_rightOperand_3_0= ruleXEqualityExpression )
            	    {
            	    // InternalDSLPolicies.g:1518:5: (lv_rightOperand_3_0= ruleXEqualityExpression )
            	    // InternalDSLPolicies.g:1519:6: lv_rightOperand_3_0= ruleXEqualityExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getXAndExpressionAccess().getRightOperandXEqualityExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_27);
            	    lv_rightOperand_3_0=ruleXEqualityExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getXAndExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"rightOperand",
            	      							lv_rightOperand_3_0,
            	      							"org.eclipse.xtext.xbase.Xbase.XEqualityExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXAndExpression"


    // $ANTLR start "entryRuleOpAnd"
    // InternalDSLPolicies.g:1541:1: entryRuleOpAnd returns [String current=null] : iv_ruleOpAnd= ruleOpAnd EOF ;
    public final String entryRuleOpAnd() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpAnd = null;


        try {
            // InternalDSLPolicies.g:1541:45: (iv_ruleOpAnd= ruleOpAnd EOF )
            // InternalDSLPolicies.g:1542:2: iv_ruleOpAnd= ruleOpAnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpAndRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpAnd=ruleOpAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpAnd.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpAnd"


    // $ANTLR start "ruleOpAnd"
    // InternalDSLPolicies.g:1548:1: ruleOpAnd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '&&' ;
    public final AntlrDatatypeRuleToken ruleOpAnd() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:1554:2: (kw= '&&' )
            // InternalDSLPolicies.g:1555:2: kw= '&&'
            {
            kw=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.getOpAndAccess().getAmpersandAmpersandKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpAnd"


    // $ANTLR start "entryRuleXEqualityExpression"
    // InternalDSLPolicies.g:1563:1: entryRuleXEqualityExpression returns [EObject current=null] : iv_ruleXEqualityExpression= ruleXEqualityExpression EOF ;
    public final EObject entryRuleXEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXEqualityExpression = null;


        try {
            // InternalDSLPolicies.g:1563:60: (iv_ruleXEqualityExpression= ruleXEqualityExpression EOF )
            // InternalDSLPolicies.g:1564:2: iv_ruleXEqualityExpression= ruleXEqualityExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXEqualityExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXEqualityExpression=ruleXEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXEqualityExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXEqualityExpression"


    // $ANTLR start "ruleXEqualityExpression"
    // InternalDSLPolicies.g:1570:1: ruleXEqualityExpression returns [EObject current=null] : (this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )* ) ;
    public final EObject ruleXEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XRelationalExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:1576:2: ( (this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )* ) )
            // InternalDSLPolicies.g:1577:2: (this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )* )
            {
            // InternalDSLPolicies.g:1577:2: (this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )* )
            // InternalDSLPolicies.g:1578:3: this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXEqualityExpressionAccess().getXRelationalExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_28);
            this_XRelationalExpression_0=ruleXRelationalExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XRelationalExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:1586:3: ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )*
            loop23:
            do {
                int alt23=2;
                switch ( input.LA(1) ) {
                case 46:
                    {
                    int LA23_2 = input.LA(2);

                    if ( (synpred4_InternalDSLPolicies()) ) {
                        alt23=1;
                    }


                    }
                    break;
                case 47:
                    {
                    int LA23_3 = input.LA(2);

                    if ( (synpred4_InternalDSLPolicies()) ) {
                        alt23=1;
                    }


                    }
                    break;
                case 48:
                    {
                    int LA23_4 = input.LA(2);

                    if ( (synpred4_InternalDSLPolicies()) ) {
                        alt23=1;
                    }


                    }
                    break;
                case 49:
                    {
                    int LA23_5 = input.LA(2);

                    if ( (synpred4_InternalDSLPolicies()) ) {
                        alt23=1;
                    }


                    }
                    break;

                }

                switch (alt23) {
            	case 1 :
            	    // InternalDSLPolicies.g:1587:4: ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) )
            	    {
            	    // InternalDSLPolicies.g:1587:4: ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) )
            	    // InternalDSLPolicies.g:1588:5: ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) )
            	    {
            	    // InternalDSLPolicies.g:1598:5: ( () ( ( ruleOpEquality ) ) )
            	    // InternalDSLPolicies.g:1599:6: () ( ( ruleOpEquality ) )
            	    {
            	    // InternalDSLPolicies.g:1599:6: ()
            	    // InternalDSLPolicies.g:1600:7: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      							current = forceCreateModelElementAndSet(
            	      								grammarAccess.getXEqualityExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
            	      								current);
            	      						
            	    }

            	    }

            	    // InternalDSLPolicies.g:1606:6: ( ( ruleOpEquality ) )
            	    // InternalDSLPolicies.g:1607:7: ( ruleOpEquality )
            	    {
            	    // InternalDSLPolicies.g:1607:7: ( ruleOpEquality )
            	    // InternalDSLPolicies.g:1608:8: ruleOpEquality
            	    {
            	    if ( state.backtracking==0 ) {

            	      								if (current==null) {
            	      									current = createModelElement(grammarAccess.getXEqualityExpressionRule());
            	      								}
            	      							
            	    }
            	    if ( state.backtracking==0 ) {

            	      								newCompositeNode(grammarAccess.getXEqualityExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
            	      							
            	    }
            	    pushFollow(FOLLOW_21);
            	    ruleOpEquality();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								afterParserOrEnumRuleCall();
            	      							
            	    }

            	    }


            	    }


            	    }


            	    }

            	    // InternalDSLPolicies.g:1624:4: ( (lv_rightOperand_3_0= ruleXRelationalExpression ) )
            	    // InternalDSLPolicies.g:1625:5: (lv_rightOperand_3_0= ruleXRelationalExpression )
            	    {
            	    // InternalDSLPolicies.g:1625:5: (lv_rightOperand_3_0= ruleXRelationalExpression )
            	    // InternalDSLPolicies.g:1626:6: lv_rightOperand_3_0= ruleXRelationalExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getXEqualityExpressionAccess().getRightOperandXRelationalExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_28);
            	    lv_rightOperand_3_0=ruleXRelationalExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getXEqualityExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"rightOperand",
            	      							lv_rightOperand_3_0,
            	      							"org.eclipse.xtext.xbase.Xbase.XRelationalExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXEqualityExpression"


    // $ANTLR start "entryRuleOpEquality"
    // InternalDSLPolicies.g:1648:1: entryRuleOpEquality returns [String current=null] : iv_ruleOpEquality= ruleOpEquality EOF ;
    public final String entryRuleOpEquality() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpEquality = null;


        try {
            // InternalDSLPolicies.g:1648:50: (iv_ruleOpEquality= ruleOpEquality EOF )
            // InternalDSLPolicies.g:1649:2: iv_ruleOpEquality= ruleOpEquality EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpEqualityRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpEquality=ruleOpEquality();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpEquality.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpEquality"


    // $ANTLR start "ruleOpEquality"
    // InternalDSLPolicies.g:1655:1: ruleOpEquality returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '==' | kw= '!=' | kw= '===' | kw= '!==' ) ;
    public final AntlrDatatypeRuleToken ruleOpEquality() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:1661:2: ( (kw= '==' | kw= '!=' | kw= '===' | kw= '!==' ) )
            // InternalDSLPolicies.g:1662:2: (kw= '==' | kw= '!=' | kw= '===' | kw= '!==' )
            {
            // InternalDSLPolicies.g:1662:2: (kw= '==' | kw= '!=' | kw= '===' | kw= '!==' )
            int alt24=4;
            switch ( input.LA(1) ) {
            case 46:
                {
                alt24=1;
                }
                break;
            case 47:
                {
                alt24=2;
                }
                break;
            case 48:
                {
                alt24=3;
                }
                break;
            case 49:
                {
                alt24=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // InternalDSLPolicies.g:1663:3: kw= '=='
                    {
                    kw=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpEqualityAccess().getEqualsSignEqualsSignKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:1669:3: kw= '!='
                    {
                    kw=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpEqualityAccess().getExclamationMarkEqualsSignKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:1675:3: kw= '==='
                    {
                    kw=(Token)match(input,48,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpEqualityAccess().getEqualsSignEqualsSignEqualsSignKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalDSLPolicies.g:1681:3: kw= '!=='
                    {
                    kw=(Token)match(input,49,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpEqualityAccess().getExclamationMarkEqualsSignEqualsSignKeyword_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpEquality"


    // $ANTLR start "entryRuleXRelationalExpression"
    // InternalDSLPolicies.g:1690:1: entryRuleXRelationalExpression returns [EObject current=null] : iv_ruleXRelationalExpression= ruleXRelationalExpression EOF ;
    public final EObject entryRuleXRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXRelationalExpression = null;


        try {
            // InternalDSLPolicies.g:1690:62: (iv_ruleXRelationalExpression= ruleXRelationalExpression EOF )
            // InternalDSLPolicies.g:1691:2: iv_ruleXRelationalExpression= ruleXRelationalExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXRelationalExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXRelationalExpression=ruleXRelationalExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXRelationalExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXRelationalExpression"


    // $ANTLR start "ruleXRelationalExpression"
    // InternalDSLPolicies.g:1697:1: ruleXRelationalExpression returns [EObject current=null] : (this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) ) | ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) ) ) )* ) ;
    public final EObject ruleXRelationalExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_XOtherOperatorExpression_0 = null;

        EObject lv_type_3_0 = null;

        EObject lv_rightOperand_6_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:1703:2: ( (this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) ) | ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) ) ) )* ) )
            // InternalDSLPolicies.g:1704:2: (this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) ) | ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) ) ) )* )
            {
            // InternalDSLPolicies.g:1704:2: (this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) ) | ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) ) ) )* )
            // InternalDSLPolicies.g:1705:3: this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) ) | ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getXOtherOperatorExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_29);
            this_XOtherOperatorExpression_0=ruleXOtherOperatorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XOtherOperatorExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:1713:3: ( ( ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) ) | ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) ) ) )*
            loop25:
            do {
                int alt25=3;
                switch ( input.LA(1) ) {
                case 41:
                    {
                    int LA25_2 = input.LA(2);

                    if ( (synpred6_InternalDSLPolicies()) ) {
                        alt25=2;
                    }


                    }
                    break;
                case 42:
                    {
                    int LA25_3 = input.LA(2);

                    if ( (synpred6_InternalDSLPolicies()) ) {
                        alt25=2;
                    }


                    }
                    break;
                case 50:
                    {
                    int LA25_4 = input.LA(2);

                    if ( (synpred5_InternalDSLPolicies()) ) {
                        alt25=1;
                    }


                    }
                    break;
                case 43:
                    {
                    int LA25_5 = input.LA(2);

                    if ( (synpred6_InternalDSLPolicies()) ) {
                        alt25=2;
                    }


                    }
                    break;

                }

                switch (alt25) {
            	case 1 :
            	    // InternalDSLPolicies.g:1714:4: ( ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )
            	    {
            	    // InternalDSLPolicies.g:1714:4: ( ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )
            	    // InternalDSLPolicies.g:1715:5: ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) )
            	    {
            	    // InternalDSLPolicies.g:1715:5: ( ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' ) )
            	    // InternalDSLPolicies.g:1716:6: ( ( () 'instanceof' ) )=> ( () otherlv_2= 'instanceof' )
            	    {
            	    // InternalDSLPolicies.g:1722:6: ( () otherlv_2= 'instanceof' )
            	    // InternalDSLPolicies.g:1723:7: () otherlv_2= 'instanceof'
            	    {
            	    // InternalDSLPolicies.g:1723:7: ()
            	    // InternalDSLPolicies.g:1724:8: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      								current = forceCreateModelElementAndSet(
            	      									grammarAccess.getXRelationalExpressionAccess().getXInstanceOfExpressionExpressionAction_1_0_0_0_0(),
            	      									current);
            	      							
            	    }

            	    }

            	    otherlv_2=(Token)match(input,50,FOLLOW_30); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							newLeafNode(otherlv_2, grammarAccess.getXRelationalExpressionAccess().getInstanceofKeyword_1_0_0_0_1());
            	      						
            	    }

            	    }


            	    }

            	    // InternalDSLPolicies.g:1736:5: ( (lv_type_3_0= ruleJvmTypeReference ) )
            	    // InternalDSLPolicies.g:1737:6: (lv_type_3_0= ruleJvmTypeReference )
            	    {
            	    // InternalDSLPolicies.g:1737:6: (lv_type_3_0= ruleJvmTypeReference )
            	    // InternalDSLPolicies.g:1738:7: lv_type_3_0= ruleJvmTypeReference
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getTypeJvmTypeReferenceParserRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_29);
            	    lv_type_3_0=ruleJvmTypeReference();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getXRelationalExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"type",
            	      								lv_type_3_0,
            	      								"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalDSLPolicies.g:1757:4: ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) ) )
            	    {
            	    // InternalDSLPolicies.g:1757:4: ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) ) )
            	    // InternalDSLPolicies.g:1758:5: ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) )
            	    {
            	    // InternalDSLPolicies.g:1758:5: ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) )
            	    // InternalDSLPolicies.g:1759:6: ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) )
            	    {
            	    // InternalDSLPolicies.g:1769:6: ( () ( ( ruleOpCompare ) ) )
            	    // InternalDSLPolicies.g:1770:7: () ( ( ruleOpCompare ) )
            	    {
            	    // InternalDSLPolicies.g:1770:7: ()
            	    // InternalDSLPolicies.g:1771:8: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      								current = forceCreateModelElementAndSet(
            	      									grammarAccess.getXRelationalExpressionAccess().getXBinaryOperationLeftOperandAction_1_1_0_0_0(),
            	      									current);
            	      							
            	    }

            	    }

            	    // InternalDSLPolicies.g:1777:7: ( ( ruleOpCompare ) )
            	    // InternalDSLPolicies.g:1778:8: ( ruleOpCompare )
            	    {
            	    // InternalDSLPolicies.g:1778:8: ( ruleOpCompare )
            	    // InternalDSLPolicies.g:1779:9: ruleOpCompare
            	    {
            	    if ( state.backtracking==0 ) {

            	      									if (current==null) {
            	      										current = createModelElement(grammarAccess.getXRelationalExpressionRule());
            	      									}
            	      								
            	    }
            	    if ( state.backtracking==0 ) {

            	      									newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_1_0_0_1_0());
            	      								
            	    }
            	    pushFollow(FOLLOW_21);
            	    ruleOpCompare();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									afterParserOrEnumRuleCall();
            	      								
            	    }

            	    }


            	    }


            	    }


            	    }

            	    // InternalDSLPolicies.g:1795:5: ( (lv_rightOperand_6_0= ruleXOtherOperatorExpression ) )
            	    // InternalDSLPolicies.g:1796:6: (lv_rightOperand_6_0= ruleXOtherOperatorExpression )
            	    {
            	    // InternalDSLPolicies.g:1796:6: (lv_rightOperand_6_0= ruleXOtherOperatorExpression )
            	    // InternalDSLPolicies.g:1797:7: lv_rightOperand_6_0= ruleXOtherOperatorExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getRightOperandXOtherOperatorExpressionParserRuleCall_1_1_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_29);
            	    lv_rightOperand_6_0=ruleXOtherOperatorExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getXRelationalExpressionRule());
            	      							}
            	      							set(
            	      								current,
            	      								"rightOperand",
            	      								lv_rightOperand_6_0,
            	      								"org.eclipse.xtext.xbase.Xbase.XOtherOperatorExpression");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXRelationalExpression"


    // $ANTLR start "entryRuleOpCompare"
    // InternalDSLPolicies.g:1820:1: entryRuleOpCompare returns [String current=null] : iv_ruleOpCompare= ruleOpCompare EOF ;
    public final String entryRuleOpCompare() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpCompare = null;


        try {
            // InternalDSLPolicies.g:1820:49: (iv_ruleOpCompare= ruleOpCompare EOF )
            // InternalDSLPolicies.g:1821:2: iv_ruleOpCompare= ruleOpCompare EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpCompareRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpCompare=ruleOpCompare();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpCompare.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpCompare"


    // $ANTLR start "ruleOpCompare"
    // InternalDSLPolicies.g:1827:1: ruleOpCompare returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '>=' | (kw= '<' kw= '=' ) | kw= '>' | kw= '<' ) ;
    public final AntlrDatatypeRuleToken ruleOpCompare() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:1833:2: ( (kw= '>=' | (kw= '<' kw= '=' ) | kw= '>' | kw= '<' ) )
            // InternalDSLPolicies.g:1834:2: (kw= '>=' | (kw= '<' kw= '=' ) | kw= '>' | kw= '<' )
            {
            // InternalDSLPolicies.g:1834:2: (kw= '>=' | (kw= '<' kw= '=' ) | kw= '>' | kw= '<' )
            int alt26=4;
            switch ( input.LA(1) ) {
            case 43:
                {
                alt26=1;
                }
                break;
            case 41:
                {
                int LA26_2 = input.LA(2);

                if ( (LA26_2==EOF||LA26_2==RULE_ID||(LA26_2>=RULE_INT && LA26_2<=RULE_DECIMAL)||LA26_2==23||LA26_2==41||(LA26_2>=57 && LA26_2<=58)||LA26_2==63||(LA26_2>=71 && LA26_2<=72)||LA26_2==74||LA26_2==77||LA26_2==79||(LA26_2>=83 && LA26_2<=85)||(LA26_2>=88 && LA26_2<=100)||LA26_2==102) ) {
                    alt26=4;
                }
                else if ( (LA26_2==16) ) {
                    alt26=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 2, input);

                    throw nvae;
                }
                }
                break;
            case 42:
                {
                alt26=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // InternalDSLPolicies.g:1835:3: kw= '>='
                    {
                    kw=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpCompareAccess().getGreaterThanSignEqualsSignKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:1841:3: (kw= '<' kw= '=' )
                    {
                    // InternalDSLPolicies.g:1841:3: (kw= '<' kw= '=' )
                    // InternalDSLPolicies.g:1842:4: kw= '<' kw= '='
                    {
                    kw=(Token)match(input,41,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpCompareAccess().getLessThanSignKeyword_1_0());
                      			
                    }
                    kw=(Token)match(input,16,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpCompareAccess().getEqualsSignKeyword_1_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:1854:3: kw= '>'
                    {
                    kw=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpCompareAccess().getGreaterThanSignKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalDSLPolicies.g:1860:3: kw= '<'
                    {
                    kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpCompareAccess().getLessThanSignKeyword_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpCompare"


    // $ANTLR start "entryRuleXOtherOperatorExpression"
    // InternalDSLPolicies.g:1869:1: entryRuleXOtherOperatorExpression returns [EObject current=null] : iv_ruleXOtherOperatorExpression= ruleXOtherOperatorExpression EOF ;
    public final EObject entryRuleXOtherOperatorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXOtherOperatorExpression = null;


        try {
            // InternalDSLPolicies.g:1869:65: (iv_ruleXOtherOperatorExpression= ruleXOtherOperatorExpression EOF )
            // InternalDSLPolicies.g:1870:2: iv_ruleXOtherOperatorExpression= ruleXOtherOperatorExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXOtherOperatorExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXOtherOperatorExpression=ruleXOtherOperatorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXOtherOperatorExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXOtherOperatorExpression"


    // $ANTLR start "ruleXOtherOperatorExpression"
    // InternalDSLPolicies.g:1876:1: ruleXOtherOperatorExpression returns [EObject current=null] : (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* ) ;
    public final EObject ruleXOtherOperatorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XAdditiveExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:1882:2: ( (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* ) )
            // InternalDSLPolicies.g:1883:2: (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* )
            {
            // InternalDSLPolicies.g:1883:2: (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* )
            // InternalDSLPolicies.g:1884:3: this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getXAdditiveExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_31);
            this_XAdditiveExpression_0=ruleXAdditiveExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XAdditiveExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:1892:3: ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )*
            loop27:
            do {
                int alt27=2;
                alt27 = dfa27.predict(input);
                switch (alt27) {
            	case 1 :
            	    // InternalDSLPolicies.g:1893:4: ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) )
            	    {
            	    // InternalDSLPolicies.g:1893:4: ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) )
            	    // InternalDSLPolicies.g:1894:5: ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) )
            	    {
            	    // InternalDSLPolicies.g:1904:5: ( () ( ( ruleOpOther ) ) )
            	    // InternalDSLPolicies.g:1905:6: () ( ( ruleOpOther ) )
            	    {
            	    // InternalDSLPolicies.g:1905:6: ()
            	    // InternalDSLPolicies.g:1906:7: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      							current = forceCreateModelElementAndSet(
            	      								grammarAccess.getXOtherOperatorExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
            	      								current);
            	      						
            	    }

            	    }

            	    // InternalDSLPolicies.g:1912:6: ( ( ruleOpOther ) )
            	    // InternalDSLPolicies.g:1913:7: ( ruleOpOther )
            	    {
            	    // InternalDSLPolicies.g:1913:7: ( ruleOpOther )
            	    // InternalDSLPolicies.g:1914:8: ruleOpOther
            	    {
            	    if ( state.backtracking==0 ) {

            	      								if (current==null) {
            	      									current = createModelElement(grammarAccess.getXOtherOperatorExpressionRule());
            	      								}
            	      							
            	    }
            	    if ( state.backtracking==0 ) {

            	      								newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
            	      							
            	    }
            	    pushFollow(FOLLOW_21);
            	    ruleOpOther();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								afterParserOrEnumRuleCall();
            	      							
            	    }

            	    }


            	    }


            	    }


            	    }

            	    // InternalDSLPolicies.g:1930:4: ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) )
            	    // InternalDSLPolicies.g:1931:5: (lv_rightOperand_3_0= ruleXAdditiveExpression )
            	    {
            	    // InternalDSLPolicies.g:1931:5: (lv_rightOperand_3_0= ruleXAdditiveExpression )
            	    // InternalDSLPolicies.g:1932:6: lv_rightOperand_3_0= ruleXAdditiveExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getRightOperandXAdditiveExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_31);
            	    lv_rightOperand_3_0=ruleXAdditiveExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getXOtherOperatorExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"rightOperand",
            	      							lv_rightOperand_3_0,
            	      							"org.eclipse.xtext.xbase.Xbase.XAdditiveExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXOtherOperatorExpression"


    // $ANTLR start "entryRuleOpOther"
    // InternalDSLPolicies.g:1954:1: entryRuleOpOther returns [String current=null] : iv_ruleOpOther= ruleOpOther EOF ;
    public final String entryRuleOpOther() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpOther = null;


        try {
            // InternalDSLPolicies.g:1954:47: (iv_ruleOpOther= ruleOpOther EOF )
            // InternalDSLPolicies.g:1955:2: iv_ruleOpOther= ruleOpOther EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpOtherRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpOther=ruleOpOther();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpOther.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpOther"


    // $ANTLR start "ruleOpOther"
    // InternalDSLPolicies.g:1961:1: ruleOpOther returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '->' | kw= '..<' | (kw= '>' kw= '..' ) | kw= '..' | kw= '=>' | (kw= '>' ( ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) ) | kw= '>' ) ) | (kw= '<' ( ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) ) | kw= '<' | kw= '=>' ) ) | kw= '<>' | kw= '?:' ) ;
    public final AntlrDatatypeRuleToken ruleOpOther() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:1967:2: ( (kw= '->' | kw= '..<' | (kw= '>' kw= '..' ) | kw= '..' | kw= '=>' | (kw= '>' ( ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) ) | kw= '>' ) ) | (kw= '<' ( ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) ) | kw= '<' | kw= '=>' ) ) | kw= '<>' | kw= '?:' ) )
            // InternalDSLPolicies.g:1968:2: (kw= '->' | kw= '..<' | (kw= '>' kw= '..' ) | kw= '..' | kw= '=>' | (kw= '>' ( ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) ) | kw= '>' ) ) | (kw= '<' ( ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) ) | kw= '<' | kw= '=>' ) ) | kw= '<>' | kw= '?:' )
            {
            // InternalDSLPolicies.g:1968:2: (kw= '->' | kw= '..<' | (kw= '>' kw= '..' ) | kw= '..' | kw= '=>' | (kw= '>' ( ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) ) | kw= '>' ) ) | (kw= '<' ( ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) ) | kw= '<' | kw= '=>' ) ) | kw= '<>' | kw= '?:' )
            int alt30=9;
            alt30 = dfa30.predict(input);
            switch (alt30) {
                case 1 :
                    // InternalDSLPolicies.g:1969:3: kw= '->'
                    {
                    kw=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpOtherAccess().getHyphenMinusGreaterThanSignKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:1975:3: kw= '..<'
                    {
                    kw=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpOtherAccess().getFullStopFullStopLessThanSignKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:1981:3: (kw= '>' kw= '..' )
                    {
                    // InternalDSLPolicies.g:1981:3: (kw= '>' kw= '..' )
                    // InternalDSLPolicies.g:1982:4: kw= '>' kw= '..'
                    {
                    kw=(Token)match(input,42,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_2_0());
                      			
                    }
                    kw=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpOtherAccess().getFullStopFullStopKeyword_2_1());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalDSLPolicies.g:1994:3: kw= '..'
                    {
                    kw=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpOtherAccess().getFullStopFullStopKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalDSLPolicies.g:2000:3: kw= '=>'
                    {
                    kw=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpOtherAccess().getEqualsSignGreaterThanSignKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalDSLPolicies.g:2006:3: (kw= '>' ( ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) ) | kw= '>' ) )
                    {
                    // InternalDSLPolicies.g:2006:3: (kw= '>' ( ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) ) | kw= '>' ) )
                    // InternalDSLPolicies.g:2007:4: kw= '>' ( ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) ) | kw= '>' )
                    {
                    kw=(Token)match(input,42,FOLLOW_33); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_5_0());
                      			
                    }
                    // InternalDSLPolicies.g:2012:4: ( ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) ) | kw= '>' )
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==42) ) {
                        int LA28_1 = input.LA(2);

                        if ( (LA28_1==EOF||LA28_1==RULE_ID||(LA28_1>=RULE_INT && LA28_1<=RULE_DECIMAL)||LA28_1==23||LA28_1==41||(LA28_1>=57 && LA28_1<=58)||LA28_1==63||(LA28_1>=71 && LA28_1<=72)||LA28_1==74||LA28_1==77||LA28_1==79||(LA28_1>=83 && LA28_1<=85)||(LA28_1>=88 && LA28_1<=100)||LA28_1==102) ) {
                            alt28=2;
                        }
                        else if ( (LA28_1==42) && (synpred8_InternalDSLPolicies())) {
                            alt28=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 28, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 0, input);

                        throw nvae;
                    }
                    switch (alt28) {
                        case 1 :
                            // InternalDSLPolicies.g:2013:5: ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) )
                            {
                            // InternalDSLPolicies.g:2013:5: ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) )
                            // InternalDSLPolicies.g:2014:6: ( ( '>' '>' ) )=> (kw= '>' kw= '>' )
                            {
                            // InternalDSLPolicies.g:2019:6: (kw= '>' kw= '>' )
                            // InternalDSLPolicies.g:2020:7: kw= '>' kw= '>'
                            {
                            kw=(Token)match(input,42,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							current.merge(kw);
                              							newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_5_1_0_0_0());
                              						
                            }
                            kw=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							current.merge(kw);
                              							newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_5_1_0_0_1());
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalDSLPolicies.g:2033:5: kw= '>'
                            {
                            kw=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_5_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalDSLPolicies.g:2041:3: (kw= '<' ( ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) ) | kw= '<' | kw= '=>' ) )
                    {
                    // InternalDSLPolicies.g:2041:3: (kw= '<' ( ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) ) | kw= '<' | kw= '=>' ) )
                    // InternalDSLPolicies.g:2042:4: kw= '<' ( ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) ) | kw= '<' | kw= '=>' )
                    {
                    kw=(Token)match(input,41,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignKeyword_6_0());
                      			
                    }
                    // InternalDSLPolicies.g:2047:4: ( ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) ) | kw= '<' | kw= '=>' )
                    int alt29=3;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==41) ) {
                        int LA29_1 = input.LA(2);

                        if ( (synpred9_InternalDSLPolicies()) ) {
                            alt29=1;
                        }
                        else if ( (true) ) {
                            alt29=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 29, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA29_0==54) ) {
                        alt29=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 29, 0, input);

                        throw nvae;
                    }
                    switch (alt29) {
                        case 1 :
                            // InternalDSLPolicies.g:2048:5: ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) )
                            {
                            // InternalDSLPolicies.g:2048:5: ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) )
                            // InternalDSLPolicies.g:2049:6: ( ( '<' '<' ) )=> (kw= '<' kw= '<' )
                            {
                            // InternalDSLPolicies.g:2054:6: (kw= '<' kw= '<' )
                            // InternalDSLPolicies.g:2055:7: kw= '<' kw= '<'
                            {
                            kw=(Token)match(input,41,FOLLOW_23); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							current.merge(kw);
                              							newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignKeyword_6_1_0_0_0());
                              						
                            }
                            kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							current.merge(kw);
                              							newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignKeyword_6_1_0_0_1());
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalDSLPolicies.g:2068:5: kw= '<'
                            {
                            kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignKeyword_6_1_1());
                              				
                            }

                            }
                            break;
                        case 3 :
                            // InternalDSLPolicies.g:2074:5: kw= '=>'
                            {
                            kw=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getOpOtherAccess().getEqualsSignGreaterThanSignKeyword_6_1_2());
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalDSLPolicies.g:2082:3: kw= '<>'
                    {
                    kw=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignGreaterThanSignKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalDSLPolicies.g:2088:3: kw= '?:'
                    {
                    kw=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpOtherAccess().getQuestionMarkColonKeyword_8());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpOther"


    // $ANTLR start "entryRuleXAdditiveExpression"
    // InternalDSLPolicies.g:2097:1: entryRuleXAdditiveExpression returns [EObject current=null] : iv_ruleXAdditiveExpression= ruleXAdditiveExpression EOF ;
    public final EObject entryRuleXAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXAdditiveExpression = null;


        try {
            // InternalDSLPolicies.g:2097:60: (iv_ruleXAdditiveExpression= ruleXAdditiveExpression EOF )
            // InternalDSLPolicies.g:2098:2: iv_ruleXAdditiveExpression= ruleXAdditiveExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXAdditiveExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXAdditiveExpression=ruleXAdditiveExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXAdditiveExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXAdditiveExpression"


    // $ANTLR start "ruleXAdditiveExpression"
    // InternalDSLPolicies.g:2104:1: ruleXAdditiveExpression returns [EObject current=null] : (this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )* ) ;
    public final EObject ruleXAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XMultiplicativeExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:2110:2: ( (this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )* ) )
            // InternalDSLPolicies.g:2111:2: (this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )* )
            {
            // InternalDSLPolicies.g:2111:2: (this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )* )
            // InternalDSLPolicies.g:2112:3: this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXAdditiveExpressionAccess().getXMultiplicativeExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_35);
            this_XMultiplicativeExpression_0=ruleXMultiplicativeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XMultiplicativeExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:2120:3: ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==57) ) {
                    int LA31_2 = input.LA(2);

                    if ( (synpred10_InternalDSLPolicies()) ) {
                        alt31=1;
                    }


                }
                else if ( (LA31_0==58) ) {
                    int LA31_3 = input.LA(2);

                    if ( (synpred10_InternalDSLPolicies()) ) {
                        alt31=1;
                    }


                }


                switch (alt31) {
            	case 1 :
            	    // InternalDSLPolicies.g:2121:4: ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) )
            	    {
            	    // InternalDSLPolicies.g:2121:4: ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) )
            	    // InternalDSLPolicies.g:2122:5: ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) )
            	    {
            	    // InternalDSLPolicies.g:2132:5: ( () ( ( ruleOpAdd ) ) )
            	    // InternalDSLPolicies.g:2133:6: () ( ( ruleOpAdd ) )
            	    {
            	    // InternalDSLPolicies.g:2133:6: ()
            	    // InternalDSLPolicies.g:2134:7: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      							current = forceCreateModelElementAndSet(
            	      								grammarAccess.getXAdditiveExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
            	      								current);
            	      						
            	    }

            	    }

            	    // InternalDSLPolicies.g:2140:6: ( ( ruleOpAdd ) )
            	    // InternalDSLPolicies.g:2141:7: ( ruleOpAdd )
            	    {
            	    // InternalDSLPolicies.g:2141:7: ( ruleOpAdd )
            	    // InternalDSLPolicies.g:2142:8: ruleOpAdd
            	    {
            	    if ( state.backtracking==0 ) {

            	      								if (current==null) {
            	      									current = createModelElement(grammarAccess.getXAdditiveExpressionRule());
            	      								}
            	      							
            	    }
            	    if ( state.backtracking==0 ) {

            	      								newCompositeNode(grammarAccess.getXAdditiveExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
            	      							
            	    }
            	    pushFollow(FOLLOW_21);
            	    ruleOpAdd();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								afterParserOrEnumRuleCall();
            	      							
            	    }

            	    }


            	    }


            	    }


            	    }

            	    // InternalDSLPolicies.g:2158:4: ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) )
            	    // InternalDSLPolicies.g:2159:5: (lv_rightOperand_3_0= ruleXMultiplicativeExpression )
            	    {
            	    // InternalDSLPolicies.g:2159:5: (lv_rightOperand_3_0= ruleXMultiplicativeExpression )
            	    // InternalDSLPolicies.g:2160:6: lv_rightOperand_3_0= ruleXMultiplicativeExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getXAdditiveExpressionAccess().getRightOperandXMultiplicativeExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_35);
            	    lv_rightOperand_3_0=ruleXMultiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getXAdditiveExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"rightOperand",
            	      							lv_rightOperand_3_0,
            	      							"org.eclipse.xtext.xbase.Xbase.XMultiplicativeExpression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXAdditiveExpression"


    // $ANTLR start "entryRuleOpAdd"
    // InternalDSLPolicies.g:2182:1: entryRuleOpAdd returns [String current=null] : iv_ruleOpAdd= ruleOpAdd EOF ;
    public final String entryRuleOpAdd() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpAdd = null;


        try {
            // InternalDSLPolicies.g:2182:45: (iv_ruleOpAdd= ruleOpAdd EOF )
            // InternalDSLPolicies.g:2183:2: iv_ruleOpAdd= ruleOpAdd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpAddRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpAdd=ruleOpAdd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpAdd.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpAdd"


    // $ANTLR start "ruleOpAdd"
    // InternalDSLPolicies.g:2189:1: ruleOpAdd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '+' | kw= '-' ) ;
    public final AntlrDatatypeRuleToken ruleOpAdd() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:2195:2: ( (kw= '+' | kw= '-' ) )
            // InternalDSLPolicies.g:2196:2: (kw= '+' | kw= '-' )
            {
            // InternalDSLPolicies.g:2196:2: (kw= '+' | kw= '-' )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==57) ) {
                alt32=1;
            }
            else if ( (LA32_0==58) ) {
                alt32=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // InternalDSLPolicies.g:2197:3: kw= '+'
                    {
                    kw=(Token)match(input,57,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpAddAccess().getPlusSignKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:2203:3: kw= '-'
                    {
                    kw=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpAddAccess().getHyphenMinusKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpAdd"


    // $ANTLR start "entryRuleXMultiplicativeExpression"
    // InternalDSLPolicies.g:2212:1: entryRuleXMultiplicativeExpression returns [EObject current=null] : iv_ruleXMultiplicativeExpression= ruleXMultiplicativeExpression EOF ;
    public final EObject entryRuleXMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXMultiplicativeExpression = null;


        try {
            // InternalDSLPolicies.g:2212:66: (iv_ruleXMultiplicativeExpression= ruleXMultiplicativeExpression EOF )
            // InternalDSLPolicies.g:2213:2: iv_ruleXMultiplicativeExpression= ruleXMultiplicativeExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXMultiplicativeExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXMultiplicativeExpression=ruleXMultiplicativeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXMultiplicativeExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXMultiplicativeExpression"


    // $ANTLR start "ruleXMultiplicativeExpression"
    // InternalDSLPolicies.g:2219:1: ruleXMultiplicativeExpression returns [EObject current=null] : (this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )* ) ;
    public final EObject ruleXMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XUnaryOperation_0 = null;

        EObject lv_rightOperand_3_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:2225:2: ( (this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )* ) )
            // InternalDSLPolicies.g:2226:2: (this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )* )
            {
            // InternalDSLPolicies.g:2226:2: (this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )* )
            // InternalDSLPolicies.g:2227:3: this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXMultiplicativeExpressionAccess().getXUnaryOperationParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_36);
            this_XUnaryOperation_0=ruleXUnaryOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XUnaryOperation_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:2235:3: ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )*
            loop33:
            do {
                int alt33=2;
                switch ( input.LA(1) ) {
                case 59:
                    {
                    int LA33_2 = input.LA(2);

                    if ( (synpred11_InternalDSLPolicies()) ) {
                        alt33=1;
                    }


                    }
                    break;
                case 60:
                    {
                    int LA33_3 = input.LA(2);

                    if ( (synpred11_InternalDSLPolicies()) ) {
                        alt33=1;
                    }


                    }
                    break;
                case 61:
                    {
                    int LA33_4 = input.LA(2);

                    if ( (synpred11_InternalDSLPolicies()) ) {
                        alt33=1;
                    }


                    }
                    break;
                case 62:
                    {
                    int LA33_5 = input.LA(2);

                    if ( (synpred11_InternalDSLPolicies()) ) {
                        alt33=1;
                    }


                    }
                    break;

                }

                switch (alt33) {
            	case 1 :
            	    // InternalDSLPolicies.g:2236:4: ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) )
            	    {
            	    // InternalDSLPolicies.g:2236:4: ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) )
            	    // InternalDSLPolicies.g:2237:5: ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) )
            	    {
            	    // InternalDSLPolicies.g:2247:5: ( () ( ( ruleOpMulti ) ) )
            	    // InternalDSLPolicies.g:2248:6: () ( ( ruleOpMulti ) )
            	    {
            	    // InternalDSLPolicies.g:2248:6: ()
            	    // InternalDSLPolicies.g:2249:7: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      							current = forceCreateModelElementAndSet(
            	      								grammarAccess.getXMultiplicativeExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
            	      								current);
            	      						
            	    }

            	    }

            	    // InternalDSLPolicies.g:2255:6: ( ( ruleOpMulti ) )
            	    // InternalDSLPolicies.g:2256:7: ( ruleOpMulti )
            	    {
            	    // InternalDSLPolicies.g:2256:7: ( ruleOpMulti )
            	    // InternalDSLPolicies.g:2257:8: ruleOpMulti
            	    {
            	    if ( state.backtracking==0 ) {

            	      								if (current==null) {
            	      									current = createModelElement(grammarAccess.getXMultiplicativeExpressionRule());
            	      								}
            	      							
            	    }
            	    if ( state.backtracking==0 ) {

            	      								newCompositeNode(grammarAccess.getXMultiplicativeExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
            	      							
            	    }
            	    pushFollow(FOLLOW_21);
            	    ruleOpMulti();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								afterParserOrEnumRuleCall();
            	      							
            	    }

            	    }


            	    }


            	    }


            	    }

            	    // InternalDSLPolicies.g:2273:4: ( (lv_rightOperand_3_0= ruleXUnaryOperation ) )
            	    // InternalDSLPolicies.g:2274:5: (lv_rightOperand_3_0= ruleXUnaryOperation )
            	    {
            	    // InternalDSLPolicies.g:2274:5: (lv_rightOperand_3_0= ruleXUnaryOperation )
            	    // InternalDSLPolicies.g:2275:6: lv_rightOperand_3_0= ruleXUnaryOperation
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getXMultiplicativeExpressionAccess().getRightOperandXUnaryOperationParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_36);
            	    lv_rightOperand_3_0=ruleXUnaryOperation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getXMultiplicativeExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"rightOperand",
            	      							lv_rightOperand_3_0,
            	      							"org.eclipse.xtext.xbase.Xbase.XUnaryOperation");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXMultiplicativeExpression"


    // $ANTLR start "entryRuleOpMulti"
    // InternalDSLPolicies.g:2297:1: entryRuleOpMulti returns [String current=null] : iv_ruleOpMulti= ruleOpMulti EOF ;
    public final String entryRuleOpMulti() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpMulti = null;


        try {
            // InternalDSLPolicies.g:2297:47: (iv_ruleOpMulti= ruleOpMulti EOF )
            // InternalDSLPolicies.g:2298:2: iv_ruleOpMulti= ruleOpMulti EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpMultiRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpMulti=ruleOpMulti();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpMulti.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpMulti"


    // $ANTLR start "ruleOpMulti"
    // InternalDSLPolicies.g:2304:1: ruleOpMulti returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | kw= '**' | kw= '/' | kw= '%' ) ;
    public final AntlrDatatypeRuleToken ruleOpMulti() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:2310:2: ( (kw= '*' | kw= '**' | kw= '/' | kw= '%' ) )
            // InternalDSLPolicies.g:2311:2: (kw= '*' | kw= '**' | kw= '/' | kw= '%' )
            {
            // InternalDSLPolicies.g:2311:2: (kw= '*' | kw= '**' | kw= '/' | kw= '%' )
            int alt34=4;
            switch ( input.LA(1) ) {
            case 59:
                {
                alt34=1;
                }
                break;
            case 60:
                {
                alt34=2;
                }
                break;
            case 61:
                {
                alt34=3;
                }
                break;
            case 62:
                {
                alt34=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }

            switch (alt34) {
                case 1 :
                    // InternalDSLPolicies.g:2312:3: kw= '*'
                    {
                    kw=(Token)match(input,59,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpMultiAccess().getAsteriskKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:2318:3: kw= '**'
                    {
                    kw=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpMultiAccess().getAsteriskAsteriskKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:2324:3: kw= '/'
                    {
                    kw=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpMultiAccess().getSolidusKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalDSLPolicies.g:2330:3: kw= '%'
                    {
                    kw=(Token)match(input,62,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpMultiAccess().getPercentSignKeyword_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpMulti"


    // $ANTLR start "entryRuleXUnaryOperation"
    // InternalDSLPolicies.g:2339:1: entryRuleXUnaryOperation returns [EObject current=null] : iv_ruleXUnaryOperation= ruleXUnaryOperation EOF ;
    public final EObject entryRuleXUnaryOperation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXUnaryOperation = null;


        try {
            // InternalDSLPolicies.g:2339:56: (iv_ruleXUnaryOperation= ruleXUnaryOperation EOF )
            // InternalDSLPolicies.g:2340:2: iv_ruleXUnaryOperation= ruleXUnaryOperation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXUnaryOperationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXUnaryOperation=ruleXUnaryOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXUnaryOperation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXUnaryOperation"


    // $ANTLR start "ruleXUnaryOperation"
    // InternalDSLPolicies.g:2346:1: ruleXUnaryOperation returns [EObject current=null] : ( ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) ) | this_XCastedExpression_3= ruleXCastedExpression ) ;
    public final EObject ruleXUnaryOperation() throws RecognitionException {
        EObject current = null;

        EObject lv_operand_2_0 = null;

        EObject this_XCastedExpression_3 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:2352:2: ( ( ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) ) | this_XCastedExpression_3= ruleXCastedExpression ) )
            // InternalDSLPolicies.g:2353:2: ( ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) ) | this_XCastedExpression_3= ruleXCastedExpression )
            {
            // InternalDSLPolicies.g:2353:2: ( ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) ) | this_XCastedExpression_3= ruleXCastedExpression )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( ((LA35_0>=57 && LA35_0<=58)||LA35_0==63) ) {
                alt35=1;
            }
            else if ( (LA35_0==RULE_ID||(LA35_0>=RULE_INT && LA35_0<=RULE_DECIMAL)||LA35_0==23||LA35_0==41||(LA35_0>=71 && LA35_0<=72)||LA35_0==74||LA35_0==77||LA35_0==79||(LA35_0>=83 && LA35_0<=85)||(LA35_0>=88 && LA35_0<=100)||LA35_0==102) ) {
                alt35=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // InternalDSLPolicies.g:2354:3: ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) )
                    {
                    // InternalDSLPolicies.g:2354:3: ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) )
                    // InternalDSLPolicies.g:2355:4: () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) )
                    {
                    // InternalDSLPolicies.g:2355:4: ()
                    // InternalDSLPolicies.g:2356:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getXUnaryOperationAccess().getXUnaryOperationAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalDSLPolicies.g:2362:4: ( ( ruleOpUnary ) )
                    // InternalDSLPolicies.g:2363:5: ( ruleOpUnary )
                    {
                    // InternalDSLPolicies.g:2363:5: ( ruleOpUnary )
                    // InternalDSLPolicies.g:2364:6: ruleOpUnary
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getXUnaryOperationRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXUnaryOperationAccess().getFeatureJvmIdentifiableElementCrossReference_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_21);
                    ruleOpUnary();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:2378:4: ( (lv_operand_2_0= ruleXUnaryOperation ) )
                    // InternalDSLPolicies.g:2379:5: (lv_operand_2_0= ruleXUnaryOperation )
                    {
                    // InternalDSLPolicies.g:2379:5: (lv_operand_2_0= ruleXUnaryOperation )
                    // InternalDSLPolicies.g:2380:6: lv_operand_2_0= ruleXUnaryOperation
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXUnaryOperationAccess().getOperandXUnaryOperationParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_operand_2_0=ruleXUnaryOperation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXUnaryOperationRule());
                      						}
                      						set(
                      							current,
                      							"operand",
                      							lv_operand_2_0,
                      							"org.eclipse.xtext.xbase.Xbase.XUnaryOperation");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:2399:3: this_XCastedExpression_3= ruleXCastedExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXUnaryOperationAccess().getXCastedExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XCastedExpression_3=ruleXCastedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XCastedExpression_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXUnaryOperation"


    // $ANTLR start "entryRuleOpUnary"
    // InternalDSLPolicies.g:2411:1: entryRuleOpUnary returns [String current=null] : iv_ruleOpUnary= ruleOpUnary EOF ;
    public final String entryRuleOpUnary() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpUnary = null;


        try {
            // InternalDSLPolicies.g:2411:47: (iv_ruleOpUnary= ruleOpUnary EOF )
            // InternalDSLPolicies.g:2412:2: iv_ruleOpUnary= ruleOpUnary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpUnaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpUnary=ruleOpUnary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpUnary.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpUnary"


    // $ANTLR start "ruleOpUnary"
    // InternalDSLPolicies.g:2418:1: ruleOpUnary returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '!' | kw= '-' | kw= '+' ) ;
    public final AntlrDatatypeRuleToken ruleOpUnary() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:2424:2: ( (kw= '!' | kw= '-' | kw= '+' ) )
            // InternalDSLPolicies.g:2425:2: (kw= '!' | kw= '-' | kw= '+' )
            {
            // InternalDSLPolicies.g:2425:2: (kw= '!' | kw= '-' | kw= '+' )
            int alt36=3;
            switch ( input.LA(1) ) {
            case 63:
                {
                alt36=1;
                }
                break;
            case 58:
                {
                alt36=2;
                }
                break;
            case 57:
                {
                alt36=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }

            switch (alt36) {
                case 1 :
                    // InternalDSLPolicies.g:2426:3: kw= '!'
                    {
                    kw=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpUnaryAccess().getExclamationMarkKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:2432:3: kw= '-'
                    {
                    kw=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpUnaryAccess().getHyphenMinusKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:2438:3: kw= '+'
                    {
                    kw=(Token)match(input,57,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpUnaryAccess().getPlusSignKeyword_2());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpUnary"


    // $ANTLR start "entryRuleXCastedExpression"
    // InternalDSLPolicies.g:2447:1: entryRuleXCastedExpression returns [EObject current=null] : iv_ruleXCastedExpression= ruleXCastedExpression EOF ;
    public final EObject entryRuleXCastedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXCastedExpression = null;


        try {
            // InternalDSLPolicies.g:2447:58: (iv_ruleXCastedExpression= ruleXCastedExpression EOF )
            // InternalDSLPolicies.g:2448:2: iv_ruleXCastedExpression= ruleXCastedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXCastedExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXCastedExpression=ruleXCastedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXCastedExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXCastedExpression"


    // $ANTLR start "ruleXCastedExpression"
    // InternalDSLPolicies.g:2454:1: ruleXCastedExpression returns [EObject current=null] : (this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )* ) ;
    public final EObject ruleXCastedExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_XPostfixOperation_0 = null;

        EObject lv_type_3_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:2460:2: ( (this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )* ) )
            // InternalDSLPolicies.g:2461:2: (this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )* )
            {
            // InternalDSLPolicies.g:2461:2: (this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )* )
            // InternalDSLPolicies.g:2462:3: this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXCastedExpressionAccess().getXPostfixOperationParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_37);
            this_XPostfixOperation_0=ruleXPostfixOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XPostfixOperation_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:2470:3: ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==64) ) {
                    int LA37_2 = input.LA(2);

                    if ( (synpred12_InternalDSLPolicies()) ) {
                        alt37=1;
                    }


                }


                switch (alt37) {
            	case 1 :
            	    // InternalDSLPolicies.g:2471:4: ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) )
            	    {
            	    // InternalDSLPolicies.g:2471:4: ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) )
            	    // InternalDSLPolicies.g:2472:5: ( ( () 'as' ) )=> ( () otherlv_2= 'as' )
            	    {
            	    // InternalDSLPolicies.g:2478:5: ( () otherlv_2= 'as' )
            	    // InternalDSLPolicies.g:2479:6: () otherlv_2= 'as'
            	    {
            	    // InternalDSLPolicies.g:2479:6: ()
            	    // InternalDSLPolicies.g:2480:7: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      							current = forceCreateModelElementAndSet(
            	      								grammarAccess.getXCastedExpressionAccess().getXCastedExpressionTargetAction_1_0_0_0(),
            	      								current);
            	      						
            	    }

            	    }

            	    otherlv_2=(Token)match(input,64,FOLLOW_30); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(otherlv_2, grammarAccess.getXCastedExpressionAccess().getAsKeyword_1_0_0_1());
            	      					
            	    }

            	    }


            	    }

            	    // InternalDSLPolicies.g:2492:4: ( (lv_type_3_0= ruleJvmTypeReference ) )
            	    // InternalDSLPolicies.g:2493:5: (lv_type_3_0= ruleJvmTypeReference )
            	    {
            	    // InternalDSLPolicies.g:2493:5: (lv_type_3_0= ruleJvmTypeReference )
            	    // InternalDSLPolicies.g:2494:6: lv_type_3_0= ruleJvmTypeReference
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getXCastedExpressionAccess().getTypeJvmTypeReferenceParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_37);
            	    lv_type_3_0=ruleJvmTypeReference();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getXCastedExpressionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"type",
            	      							lv_type_3_0,
            	      							"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXCastedExpression"


    // $ANTLR start "entryRuleXPostfixOperation"
    // InternalDSLPolicies.g:2516:1: entryRuleXPostfixOperation returns [EObject current=null] : iv_ruleXPostfixOperation= ruleXPostfixOperation EOF ;
    public final EObject entryRuleXPostfixOperation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXPostfixOperation = null;


        try {
            // InternalDSLPolicies.g:2516:58: (iv_ruleXPostfixOperation= ruleXPostfixOperation EOF )
            // InternalDSLPolicies.g:2517:2: iv_ruleXPostfixOperation= ruleXPostfixOperation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXPostfixOperationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXPostfixOperation=ruleXPostfixOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXPostfixOperation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXPostfixOperation"


    // $ANTLR start "ruleXPostfixOperation"
    // InternalDSLPolicies.g:2523:1: ruleXPostfixOperation returns [EObject current=null] : (this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )? ) ;
    public final EObject ruleXPostfixOperation() throws RecognitionException {
        EObject current = null;

        EObject this_XMemberFeatureCall_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:2529:2: ( (this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )? ) )
            // InternalDSLPolicies.g:2530:2: (this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )? )
            {
            // InternalDSLPolicies.g:2530:2: (this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )? )
            // InternalDSLPolicies.g:2531:3: this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXPostfixOperationAccess().getXMemberFeatureCallParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_38);
            this_XMemberFeatureCall_0=ruleXMemberFeatureCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XMemberFeatureCall_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:2539:3: ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==65) ) {
                int LA38_1 = input.LA(2);

                if ( (synpred13_InternalDSLPolicies()) ) {
                    alt38=1;
                }
            }
            else if ( (LA38_0==66) ) {
                int LA38_2 = input.LA(2);

                if ( (synpred13_InternalDSLPolicies()) ) {
                    alt38=1;
                }
            }
            switch (alt38) {
                case 1 :
                    // InternalDSLPolicies.g:2540:4: ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) )
                    {
                    // InternalDSLPolicies.g:2550:4: ( () ( ( ruleOpPostfix ) ) )
                    // InternalDSLPolicies.g:2551:5: () ( ( ruleOpPostfix ) )
                    {
                    // InternalDSLPolicies.g:2551:5: ()
                    // InternalDSLPolicies.g:2552:6: 
                    {
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElementAndSet(
                      							grammarAccess.getXPostfixOperationAccess().getXPostfixOperationOperandAction_1_0_0(),
                      							current);
                      					
                    }

                    }

                    // InternalDSLPolicies.g:2558:5: ( ( ruleOpPostfix ) )
                    // InternalDSLPolicies.g:2559:6: ( ruleOpPostfix )
                    {
                    // InternalDSLPolicies.g:2559:6: ( ruleOpPostfix )
                    // InternalDSLPolicies.g:2560:7: ruleOpPostfix
                    {
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getXPostfixOperationRule());
                      							}
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getXPostfixOperationAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    ruleOpPostfix();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXPostfixOperation"


    // $ANTLR start "entryRuleOpPostfix"
    // InternalDSLPolicies.g:2580:1: entryRuleOpPostfix returns [String current=null] : iv_ruleOpPostfix= ruleOpPostfix EOF ;
    public final String entryRuleOpPostfix() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpPostfix = null;


        try {
            // InternalDSLPolicies.g:2580:49: (iv_ruleOpPostfix= ruleOpPostfix EOF )
            // InternalDSLPolicies.g:2581:2: iv_ruleOpPostfix= ruleOpPostfix EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpPostfixRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOpPostfix=ruleOpPostfix();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpPostfix.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOpPostfix"


    // $ANTLR start "ruleOpPostfix"
    // InternalDSLPolicies.g:2587:1: ruleOpPostfix returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '++' | kw= '--' ) ;
    public final AntlrDatatypeRuleToken ruleOpPostfix() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:2593:2: ( (kw= '++' | kw= '--' ) )
            // InternalDSLPolicies.g:2594:2: (kw= '++' | kw= '--' )
            {
            // InternalDSLPolicies.g:2594:2: (kw= '++' | kw= '--' )
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==65) ) {
                alt39=1;
            }
            else if ( (LA39_0==66) ) {
                alt39=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }
            switch (alt39) {
                case 1 :
                    // InternalDSLPolicies.g:2595:3: kw= '++'
                    {
                    kw=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpPostfixAccess().getPlusSignPlusSignKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:2601:3: kw= '--'
                    {
                    kw=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getOpPostfixAccess().getHyphenMinusHyphenMinusKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOpPostfix"


    // $ANTLR start "entryRuleXMemberFeatureCall"
    // InternalDSLPolicies.g:2610:1: entryRuleXMemberFeatureCall returns [EObject current=null] : iv_ruleXMemberFeatureCall= ruleXMemberFeatureCall EOF ;
    public final EObject entryRuleXMemberFeatureCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXMemberFeatureCall = null;


        try {
            // InternalDSLPolicies.g:2610:59: (iv_ruleXMemberFeatureCall= ruleXMemberFeatureCall EOF )
            // InternalDSLPolicies.g:2611:2: iv_ruleXMemberFeatureCall= ruleXMemberFeatureCall EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXMemberFeatureCallRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXMemberFeatureCall=ruleXMemberFeatureCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXMemberFeatureCall; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXMemberFeatureCall"


    // $ANTLR start "ruleXMemberFeatureCall"
    // InternalDSLPolicies.g:2617:1: ruleXMemberFeatureCall returns [EObject current=null] : (this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )* ) ;
    public final EObject ruleXMemberFeatureCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_explicitStatic_3_0=null;
        Token otherlv_8=null;
        Token lv_nullSafe_9_0=null;
        Token lv_explicitStatic_10_0=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token lv_explicitOperationCall_17_0=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        EObject this_XPrimaryExpression_0 = null;

        EObject lv_value_6_0 = null;

        EObject lv_typeArguments_12_0 = null;

        EObject lv_typeArguments_14_0 = null;

        EObject lv_memberCallArguments_18_0 = null;

        EObject lv_memberCallArguments_19_0 = null;

        EObject lv_memberCallArguments_21_0 = null;

        EObject lv_memberCallArguments_23_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:2623:2: ( (this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )* ) )
            // InternalDSLPolicies.g:2624:2: (this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )* )
            {
            // InternalDSLPolicies.g:2624:2: (this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )* )
            // InternalDSLPolicies.g:2625:3: this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getXPrimaryExpressionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_39);
            this_XPrimaryExpression_0=ruleXPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XPrimaryExpression_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:2633:3: ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )*
            loop48:
            do {
                int alt48=3;
                switch ( input.LA(1) ) {
                case 67:
                    {
                    int LA48_2 = input.LA(2);

                    if ( (synpred14_InternalDSLPolicies()) ) {
                        alt48=1;
                    }
                    else if ( (synpred15_InternalDSLPolicies()) ) {
                        alt48=2;
                    }


                    }
                    break;
                case 68:
                    {
                    int LA48_3 = input.LA(2);

                    if ( (synpred14_InternalDSLPolicies()) ) {
                        alt48=1;
                    }
                    else if ( (synpred15_InternalDSLPolicies()) ) {
                        alt48=2;
                    }


                    }
                    break;
                case 69:
                    {
                    int LA48_4 = input.LA(2);

                    if ( (synpred15_InternalDSLPolicies()) ) {
                        alt48=2;
                    }


                    }
                    break;

                }

                switch (alt48) {
            	case 1 :
            	    // InternalDSLPolicies.g:2634:4: ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) )
            	    {
            	    // InternalDSLPolicies.g:2634:4: ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) )
            	    // InternalDSLPolicies.g:2635:5: ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) )
            	    {
            	    // InternalDSLPolicies.g:2635:5: ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )
            	    // InternalDSLPolicies.g:2636:6: ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign )
            	    {
            	    // InternalDSLPolicies.g:2656:6: ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign )
            	    // InternalDSLPolicies.g:2657:7: () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign
            	    {
            	    // InternalDSLPolicies.g:2657:7: ()
            	    // InternalDSLPolicies.g:2658:8: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      								current = forceCreateModelElementAndSet(
            	      									grammarAccess.getXMemberFeatureCallAccess().getXAssignmentAssignableAction_1_0_0_0_0(),
            	      									current);
            	      							
            	    }

            	    }

            	    // InternalDSLPolicies.g:2664:7: (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) )
            	    int alt40=2;
            	    int LA40_0 = input.LA(1);

            	    if ( (LA40_0==67) ) {
            	        alt40=1;
            	    }
            	    else if ( (LA40_0==68) ) {
            	        alt40=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 40, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt40) {
            	        case 1 :
            	            // InternalDSLPolicies.g:2665:8: otherlv_2= '.'
            	            {
            	            otherlv_2=(Token)match(input,67,FOLLOW_40); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(otherlv_2, grammarAccess.getXMemberFeatureCallAccess().getFullStopKeyword_1_0_0_0_1_0());
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalDSLPolicies.g:2670:8: ( (lv_explicitStatic_3_0= '::' ) )
            	            {
            	            // InternalDSLPolicies.g:2670:8: ( (lv_explicitStatic_3_0= '::' ) )
            	            // InternalDSLPolicies.g:2671:9: (lv_explicitStatic_3_0= '::' )
            	            {
            	            // InternalDSLPolicies.g:2671:9: (lv_explicitStatic_3_0= '::' )
            	            // InternalDSLPolicies.g:2672:10: lv_explicitStatic_3_0= '::'
            	            {
            	            lv_explicitStatic_3_0=(Token)match(input,68,FOLLOW_40); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(lv_explicitStatic_3_0, grammarAccess.getXMemberFeatureCallAccess().getExplicitStaticColonColonKeyword_1_0_0_0_1_1_0());
            	              									
            	            }
            	            if ( state.backtracking==0 ) {

            	              										if (current==null) {
            	              											current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
            	              										}
            	              										setWithLastConsumed(current, "explicitStatic", true, "::");
            	              									
            	            }

            	            }


            	            }


            	            }
            	            break;

            	    }

            	    // InternalDSLPolicies.g:2685:7: ( ( ruleFeatureCallID ) )
            	    // InternalDSLPolicies.g:2686:8: ( ruleFeatureCallID )
            	    {
            	    // InternalDSLPolicies.g:2686:8: ( ruleFeatureCallID )
            	    // InternalDSLPolicies.g:2687:9: ruleFeatureCallID
            	    {
            	    if ( state.backtracking==0 ) {

            	      									if (current==null) {
            	      										current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
            	      									}
            	      								
            	    }
            	    if ( state.backtracking==0 ) {

            	      									newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_0_2_0());
            	      								
            	    }
            	    pushFollow(FOLLOW_7);
            	    ruleFeatureCallID();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									afterParserOrEnumRuleCall();
            	      								
            	    }

            	    }


            	    }

            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getOpSingleAssignParserRuleCall_1_0_0_0_3());
            	      						
            	    }
            	    pushFollow(FOLLOW_21);
            	    ruleOpSingleAssign();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    // InternalDSLPolicies.g:2710:5: ( (lv_value_6_0= ruleXAssignment ) )
            	    // InternalDSLPolicies.g:2711:6: (lv_value_6_0= ruleXAssignment )
            	    {
            	    // InternalDSLPolicies.g:2711:6: (lv_value_6_0= ruleXAssignment )
            	    // InternalDSLPolicies.g:2712:7: lv_value_6_0= ruleXAssignment
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getValueXAssignmentParserRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_39);
            	    lv_value_6_0=ruleXAssignment();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
            	      							}
            	      							set(
            	      								current,
            	      								"value",
            	      								lv_value_6_0,
            	      								"org.eclipse.xtext.xbase.Xbase.XAssignment");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalDSLPolicies.g:2731:4: ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? )
            	    {
            	    // InternalDSLPolicies.g:2731:4: ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? )
            	    // InternalDSLPolicies.g:2732:5: ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )?
            	    {
            	    // InternalDSLPolicies.g:2732:5: ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) )
            	    // InternalDSLPolicies.g:2733:6: ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) )
            	    {
            	    // InternalDSLPolicies.g:2753:6: ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) )
            	    // InternalDSLPolicies.g:2754:7: () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) )
            	    {
            	    // InternalDSLPolicies.g:2754:7: ()
            	    // InternalDSLPolicies.g:2755:8: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      								current = forceCreateModelElementAndSet(
            	      									grammarAccess.getXMemberFeatureCallAccess().getXMemberFeatureCallMemberCallTargetAction_1_1_0_0_0(),
            	      									current);
            	      							
            	    }

            	    }

            	    // InternalDSLPolicies.g:2761:7: (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) )
            	    int alt41=3;
            	    switch ( input.LA(1) ) {
            	    case 67:
            	        {
            	        alt41=1;
            	        }
            	        break;
            	    case 69:
            	        {
            	        alt41=2;
            	        }
            	        break;
            	    case 68:
            	        {
            	        alt41=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 41, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt41) {
            	        case 1 :
            	            // InternalDSLPolicies.g:2762:8: otherlv_8= '.'
            	            {
            	            otherlv_8=(Token)match(input,67,FOLLOW_41); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(otherlv_8, grammarAccess.getXMemberFeatureCallAccess().getFullStopKeyword_1_1_0_0_1_0());
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalDSLPolicies.g:2767:8: ( (lv_nullSafe_9_0= '?.' ) )
            	            {
            	            // InternalDSLPolicies.g:2767:8: ( (lv_nullSafe_9_0= '?.' ) )
            	            // InternalDSLPolicies.g:2768:9: (lv_nullSafe_9_0= '?.' )
            	            {
            	            // InternalDSLPolicies.g:2768:9: (lv_nullSafe_9_0= '?.' )
            	            // InternalDSLPolicies.g:2769:10: lv_nullSafe_9_0= '?.'
            	            {
            	            lv_nullSafe_9_0=(Token)match(input,69,FOLLOW_41); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(lv_nullSafe_9_0, grammarAccess.getXMemberFeatureCallAccess().getNullSafeQuestionMarkFullStopKeyword_1_1_0_0_1_1_0());
            	              									
            	            }
            	            if ( state.backtracking==0 ) {

            	              										if (current==null) {
            	              											current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
            	              										}
            	              										setWithLastConsumed(current, "nullSafe", true, "?.");
            	              									
            	            }

            	            }


            	            }


            	            }
            	            break;
            	        case 3 :
            	            // InternalDSLPolicies.g:2782:8: ( (lv_explicitStatic_10_0= '::' ) )
            	            {
            	            // InternalDSLPolicies.g:2782:8: ( (lv_explicitStatic_10_0= '::' ) )
            	            // InternalDSLPolicies.g:2783:9: (lv_explicitStatic_10_0= '::' )
            	            {
            	            // InternalDSLPolicies.g:2783:9: (lv_explicitStatic_10_0= '::' )
            	            // InternalDSLPolicies.g:2784:10: lv_explicitStatic_10_0= '::'
            	            {
            	            lv_explicitStatic_10_0=(Token)match(input,68,FOLLOW_41); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(lv_explicitStatic_10_0, grammarAccess.getXMemberFeatureCallAccess().getExplicitStaticColonColonKeyword_1_1_0_0_1_2_0());
            	              									
            	            }
            	            if ( state.backtracking==0 ) {

            	              										if (current==null) {
            	              											current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
            	              										}
            	              										setWithLastConsumed(current, "explicitStatic", true, "::");
            	              									
            	            }

            	            }


            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalDSLPolicies.g:2799:5: (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )?
            	    int alt43=2;
            	    int LA43_0 = input.LA(1);

            	    if ( (LA43_0==41) ) {
            	        alt43=1;
            	    }
            	    switch (alt43) {
            	        case 1 :
            	            // InternalDSLPolicies.g:2800:6: otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>'
            	            {
            	            otherlv_11=(Token)match(input,41,FOLLOW_42); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(otherlv_11, grammarAccess.getXMemberFeatureCallAccess().getLessThanSignKeyword_1_1_1_0());
            	              					
            	            }
            	            // InternalDSLPolicies.g:2804:6: ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) )
            	            // InternalDSLPolicies.g:2805:7: (lv_typeArguments_12_0= ruleJvmArgumentTypeReference )
            	            {
            	            // InternalDSLPolicies.g:2805:7: (lv_typeArguments_12_0= ruleJvmArgumentTypeReference )
            	            // InternalDSLPolicies.g:2806:8: lv_typeArguments_12_0= ruleJvmArgumentTypeReference
            	            {
            	            if ( state.backtracking==0 ) {

            	              								newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_1_1_0());
            	              							
            	            }
            	            pushFollow(FOLLOW_43);
            	            lv_typeArguments_12_0=ruleJvmArgumentTypeReference();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
            	              								}
            	              								add(
            	              									current,
            	              									"typeArguments",
            	              									lv_typeArguments_12_0,
            	              									"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
            	              								afterParserOrEnumRuleCall();
            	              							
            	            }

            	            }


            	            }

            	            // InternalDSLPolicies.g:2823:6: (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )*
            	            loop42:
            	            do {
            	                int alt42=2;
            	                int LA42_0 = input.LA(1);

            	                if ( (LA42_0==70) ) {
            	                    alt42=1;
            	                }


            	                switch (alt42) {
            	            	case 1 :
            	            	    // InternalDSLPolicies.g:2824:7: otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) )
            	            	    {
            	            	    otherlv_13=(Token)match(input,70,FOLLOW_42); if (state.failed) return current;
            	            	    if ( state.backtracking==0 ) {

            	            	      							newLeafNode(otherlv_13, grammarAccess.getXMemberFeatureCallAccess().getCommaKeyword_1_1_1_2_0());
            	            	      						
            	            	    }
            	            	    // InternalDSLPolicies.g:2828:7: ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) )
            	            	    // InternalDSLPolicies.g:2829:8: (lv_typeArguments_14_0= ruleJvmArgumentTypeReference )
            	            	    {
            	            	    // InternalDSLPolicies.g:2829:8: (lv_typeArguments_14_0= ruleJvmArgumentTypeReference )
            	            	    // InternalDSLPolicies.g:2830:9: lv_typeArguments_14_0= ruleJvmArgumentTypeReference
            	            	    {
            	            	    if ( state.backtracking==0 ) {

            	            	      									newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_1_2_1_0());
            	            	      								
            	            	    }
            	            	    pushFollow(FOLLOW_43);
            	            	    lv_typeArguments_14_0=ruleJvmArgumentTypeReference();

            	            	    state._fsp--;
            	            	    if (state.failed) return current;
            	            	    if ( state.backtracking==0 ) {

            	            	      									if (current==null) {
            	            	      										current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
            	            	      									}
            	            	      									add(
            	            	      										current,
            	            	      										"typeArguments",
            	            	      										lv_typeArguments_14_0,
            	            	      										"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
            	            	      									afterParserOrEnumRuleCall();
            	            	      								
            	            	    }

            	            	    }


            	            	    }


            	            	    }
            	            	    break;

            	            	default :
            	            	    break loop42;
            	                }
            	            } while (true);

            	            otherlv_15=(Token)match(input,42,FOLLOW_41); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(otherlv_15, grammarAccess.getXMemberFeatureCallAccess().getGreaterThanSignKeyword_1_1_1_3());
            	              					
            	            }

            	            }
            	            break;

            	    }

            	    // InternalDSLPolicies.g:2853:5: ( ( ruleIdOrSuper ) )
            	    // InternalDSLPolicies.g:2854:6: ( ruleIdOrSuper )
            	    {
            	    // InternalDSLPolicies.g:2854:6: ( ruleIdOrSuper )
            	    // InternalDSLPolicies.g:2855:7: ruleIdOrSuper
            	    {
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
            	      							}
            	      						
            	    }
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getFeatureJvmIdentifiableElementCrossReference_1_1_2_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_44);
            	    ruleIdOrSuper();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    // InternalDSLPolicies.g:2869:5: ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )?
            	    int alt46=2;
            	    alt46 = dfa46.predict(input);
            	    switch (alt46) {
            	        case 1 :
            	            // InternalDSLPolicies.g:2870:6: ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')'
            	            {
            	            // InternalDSLPolicies.g:2870:6: ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) )
            	            // InternalDSLPolicies.g:2871:7: ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' )
            	            {
            	            // InternalDSLPolicies.g:2875:7: (lv_explicitOperationCall_17_0= '(' )
            	            // InternalDSLPolicies.g:2876:8: lv_explicitOperationCall_17_0= '('
            	            {
            	            lv_explicitOperationCall_17_0=(Token)match(input,23,FOLLOW_45); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_explicitOperationCall_17_0, grammarAccess.getXMemberFeatureCallAccess().getExplicitOperationCallLeftParenthesisKeyword_1_1_3_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
            	              								}
            	              								setWithLastConsumed(current, "explicitOperationCall", true, "(");
            	              							
            	            }

            	            }


            	            }

            	            // InternalDSLPolicies.g:2888:6: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )?
            	            int alt45=3;
            	            alt45 = dfa45.predict(input);
            	            switch (alt45) {
            	                case 1 :
            	                    // InternalDSLPolicies.g:2889:7: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) )
            	                    {
            	                    // InternalDSLPolicies.g:2889:7: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) )
            	                    // InternalDSLPolicies.g:2890:8: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure )
            	                    {
            	                    // InternalDSLPolicies.g:2915:8: (lv_memberCallArguments_18_0= ruleXShortClosure )
            	                    // InternalDSLPolicies.g:2916:9: lv_memberCallArguments_18_0= ruleXShortClosure
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      									newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXShortClosureParserRuleCall_1_1_3_1_0_0());
            	                      								
            	                    }
            	                    pushFollow(FOLLOW_17);
            	                    lv_memberCallArguments_18_0=ruleXShortClosure();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      									if (current==null) {
            	                      										current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
            	                      									}
            	                      									add(
            	                      										current,
            	                      										"memberCallArguments",
            	                      										lv_memberCallArguments_18_0,
            	                      										"org.eclipse.xtext.xbase.Xbase.XShortClosure");
            	                      									afterParserOrEnumRuleCall();
            	                      								
            	                    }

            	                    }


            	                    }


            	                    }
            	                    break;
            	                case 2 :
            	                    // InternalDSLPolicies.g:2934:7: ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* )
            	                    {
            	                    // InternalDSLPolicies.g:2934:7: ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* )
            	                    // InternalDSLPolicies.g:2935:8: ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )*
            	                    {
            	                    // InternalDSLPolicies.g:2935:8: ( (lv_memberCallArguments_19_0= ruleXExpression ) )
            	                    // InternalDSLPolicies.g:2936:9: (lv_memberCallArguments_19_0= ruleXExpression )
            	                    {
            	                    // InternalDSLPolicies.g:2936:9: (lv_memberCallArguments_19_0= ruleXExpression )
            	                    // InternalDSLPolicies.g:2937:10: lv_memberCallArguments_19_0= ruleXExpression
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      										newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXExpressionParserRuleCall_1_1_3_1_1_0_0());
            	                      									
            	                    }
            	                    pushFollow(FOLLOW_46);
            	                    lv_memberCallArguments_19_0=ruleXExpression();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      										if (current==null) {
            	                      											current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
            	                      										}
            	                      										add(
            	                      											current,
            	                      											"memberCallArguments",
            	                      											lv_memberCallArguments_19_0,
            	                      											"org.eclipse.xtext.xbase.Xbase.XExpression");
            	                      										afterParserOrEnumRuleCall();
            	                      									
            	                    }

            	                    }


            	                    }

            	                    // InternalDSLPolicies.g:2954:8: (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )*
            	                    loop44:
            	                    do {
            	                        int alt44=2;
            	                        int LA44_0 = input.LA(1);

            	                        if ( (LA44_0==70) ) {
            	                            alt44=1;
            	                        }


            	                        switch (alt44) {
            	                    	case 1 :
            	                    	    // InternalDSLPolicies.g:2955:9: otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) )
            	                    	    {
            	                    	    otherlv_20=(Token)match(input,70,FOLLOW_21); if (state.failed) return current;
            	                    	    if ( state.backtracking==0 ) {

            	                    	      									newLeafNode(otherlv_20, grammarAccess.getXMemberFeatureCallAccess().getCommaKeyword_1_1_3_1_1_1_0());
            	                    	      								
            	                    	    }
            	                    	    // InternalDSLPolicies.g:2959:9: ( (lv_memberCallArguments_21_0= ruleXExpression ) )
            	                    	    // InternalDSLPolicies.g:2960:10: (lv_memberCallArguments_21_0= ruleXExpression )
            	                    	    {
            	                    	    // InternalDSLPolicies.g:2960:10: (lv_memberCallArguments_21_0= ruleXExpression )
            	                    	    // InternalDSLPolicies.g:2961:11: lv_memberCallArguments_21_0= ruleXExpression
            	                    	    {
            	                    	    if ( state.backtracking==0 ) {

            	                    	      											newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXExpressionParserRuleCall_1_1_3_1_1_1_1_0());
            	                    	      										
            	                    	    }
            	                    	    pushFollow(FOLLOW_46);
            	                    	    lv_memberCallArguments_21_0=ruleXExpression();

            	                    	    state._fsp--;
            	                    	    if (state.failed) return current;
            	                    	    if ( state.backtracking==0 ) {

            	                    	      											if (current==null) {
            	                    	      												current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
            	                    	      											}
            	                    	      											add(
            	                    	      												current,
            	                    	      												"memberCallArguments",
            	                    	      												lv_memberCallArguments_21_0,
            	                    	      												"org.eclipse.xtext.xbase.Xbase.XExpression");
            	                    	      											afterParserOrEnumRuleCall();
            	                    	      										
            	                    	    }

            	                    	    }


            	                    	    }


            	                    	    }
            	                    	    break;

            	                    	default :
            	                    	    break loop44;
            	                        }
            	                    } while (true);


            	                    }


            	                    }
            	                    break;

            	            }

            	            otherlv_22=(Token)match(input,24,FOLLOW_47); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(otherlv_22, grammarAccess.getXMemberFeatureCallAccess().getRightParenthesisKeyword_1_1_3_2());
            	              					
            	            }

            	            }
            	            break;

            	    }

            	    // InternalDSLPolicies.g:2986:5: ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )?
            	    int alt47=2;
            	    alt47 = dfa47.predict(input);
            	    switch (alt47) {
            	        case 1 :
            	            // InternalDSLPolicies.g:2987:6: ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure )
            	            {
            	            // InternalDSLPolicies.g:2993:6: (lv_memberCallArguments_23_0= ruleXClosure )
            	            // InternalDSLPolicies.g:2994:7: lv_memberCallArguments_23_0= ruleXClosure
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXClosureParserRuleCall_1_1_4_0());
            	              						
            	            }
            	            pushFollow(FOLLOW_39);
            	            lv_memberCallArguments_23_0=ruleXClosure();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
            	              							}
            	              							add(
            	              								current,
            	              								"memberCallArguments",
            	              								lv_memberCallArguments_23_0,
            	              								"org.eclipse.xtext.xbase.Xbase.XClosure");
            	              							afterParserOrEnumRuleCall();
            	              						
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXMemberFeatureCall"


    // $ANTLR start "entryRuleXPrimaryExpression"
    // InternalDSLPolicies.g:3017:1: entryRuleXPrimaryExpression returns [EObject current=null] : iv_ruleXPrimaryExpression= ruleXPrimaryExpression EOF ;
    public final EObject entryRuleXPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXPrimaryExpression = null;


        try {
            // InternalDSLPolicies.g:3017:59: (iv_ruleXPrimaryExpression= ruleXPrimaryExpression EOF )
            // InternalDSLPolicies.g:3018:2: iv_ruleXPrimaryExpression= ruleXPrimaryExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXPrimaryExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXPrimaryExpression=ruleXPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXPrimaryExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXPrimaryExpression"


    // $ANTLR start "ruleXPrimaryExpression"
    // InternalDSLPolicies.g:3024:1: ruleXPrimaryExpression returns [EObject current=null] : (this_XConstructorCall_0= ruleXConstructorCall | this_XBlockExpression_1= ruleXBlockExpression | this_XSwitchExpression_2= ruleXSwitchExpression | ( ( ( () 'synchronized' '(' ) )=>this_XSynchronizedExpression_3= ruleXSynchronizedExpression ) | this_XFeatureCall_4= ruleXFeatureCall | this_XLiteral_5= ruleXLiteral | this_XIfExpression_6= ruleXIfExpression | ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=>this_XForLoopExpression_7= ruleXForLoopExpression ) | this_XBasicForLoopExpression_8= ruleXBasicForLoopExpression | this_XWhileExpression_9= ruleXWhileExpression | this_XDoWhileExpression_10= ruleXDoWhileExpression | this_XThrowExpression_11= ruleXThrowExpression | this_XReturnExpression_12= ruleXReturnExpression | this_XTryCatchFinallyExpression_13= ruleXTryCatchFinallyExpression | this_XParenthesizedExpression_14= ruleXParenthesizedExpression ) ;
    public final EObject ruleXPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XConstructorCall_0 = null;

        EObject this_XBlockExpression_1 = null;

        EObject this_XSwitchExpression_2 = null;

        EObject this_XSynchronizedExpression_3 = null;

        EObject this_XFeatureCall_4 = null;

        EObject this_XLiteral_5 = null;

        EObject this_XIfExpression_6 = null;

        EObject this_XForLoopExpression_7 = null;

        EObject this_XBasicForLoopExpression_8 = null;

        EObject this_XWhileExpression_9 = null;

        EObject this_XDoWhileExpression_10 = null;

        EObject this_XThrowExpression_11 = null;

        EObject this_XReturnExpression_12 = null;

        EObject this_XTryCatchFinallyExpression_13 = null;

        EObject this_XParenthesizedExpression_14 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3030:2: ( (this_XConstructorCall_0= ruleXConstructorCall | this_XBlockExpression_1= ruleXBlockExpression | this_XSwitchExpression_2= ruleXSwitchExpression | ( ( ( () 'synchronized' '(' ) )=>this_XSynchronizedExpression_3= ruleXSynchronizedExpression ) | this_XFeatureCall_4= ruleXFeatureCall | this_XLiteral_5= ruleXLiteral | this_XIfExpression_6= ruleXIfExpression | ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=>this_XForLoopExpression_7= ruleXForLoopExpression ) | this_XBasicForLoopExpression_8= ruleXBasicForLoopExpression | this_XWhileExpression_9= ruleXWhileExpression | this_XDoWhileExpression_10= ruleXDoWhileExpression | this_XThrowExpression_11= ruleXThrowExpression | this_XReturnExpression_12= ruleXReturnExpression | this_XTryCatchFinallyExpression_13= ruleXTryCatchFinallyExpression | this_XParenthesizedExpression_14= ruleXParenthesizedExpression ) )
            // InternalDSLPolicies.g:3031:2: (this_XConstructorCall_0= ruleXConstructorCall | this_XBlockExpression_1= ruleXBlockExpression | this_XSwitchExpression_2= ruleXSwitchExpression | ( ( ( () 'synchronized' '(' ) )=>this_XSynchronizedExpression_3= ruleXSynchronizedExpression ) | this_XFeatureCall_4= ruleXFeatureCall | this_XLiteral_5= ruleXLiteral | this_XIfExpression_6= ruleXIfExpression | ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=>this_XForLoopExpression_7= ruleXForLoopExpression ) | this_XBasicForLoopExpression_8= ruleXBasicForLoopExpression | this_XWhileExpression_9= ruleXWhileExpression | this_XDoWhileExpression_10= ruleXDoWhileExpression | this_XThrowExpression_11= ruleXThrowExpression | this_XReturnExpression_12= ruleXReturnExpression | this_XTryCatchFinallyExpression_13= ruleXTryCatchFinallyExpression | this_XParenthesizedExpression_14= ruleXParenthesizedExpression )
            {
            // InternalDSLPolicies.g:3031:2: (this_XConstructorCall_0= ruleXConstructorCall | this_XBlockExpression_1= ruleXBlockExpression | this_XSwitchExpression_2= ruleXSwitchExpression | ( ( ( () 'synchronized' '(' ) )=>this_XSynchronizedExpression_3= ruleXSynchronizedExpression ) | this_XFeatureCall_4= ruleXFeatureCall | this_XLiteral_5= ruleXLiteral | this_XIfExpression_6= ruleXIfExpression | ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=>this_XForLoopExpression_7= ruleXForLoopExpression ) | this_XBasicForLoopExpression_8= ruleXBasicForLoopExpression | this_XWhileExpression_9= ruleXWhileExpression | this_XDoWhileExpression_10= ruleXDoWhileExpression | this_XThrowExpression_11= ruleXThrowExpression | this_XReturnExpression_12= ruleXReturnExpression | this_XTryCatchFinallyExpression_13= ruleXTryCatchFinallyExpression | this_XParenthesizedExpression_14= ruleXParenthesizedExpression )
            int alt49=15;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // InternalDSLPolicies.g:3032:3: this_XConstructorCall_0= ruleXConstructorCall
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXConstructorCallParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XConstructorCall_0=ruleXConstructorCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XConstructorCall_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:3041:3: this_XBlockExpression_1= ruleXBlockExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXBlockExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XBlockExpression_1=ruleXBlockExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XBlockExpression_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:3050:3: this_XSwitchExpression_2= ruleXSwitchExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXSwitchExpressionParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XSwitchExpression_2=ruleXSwitchExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XSwitchExpression_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalDSLPolicies.g:3059:3: ( ( ( () 'synchronized' '(' ) )=>this_XSynchronizedExpression_3= ruleXSynchronizedExpression )
                    {
                    // InternalDSLPolicies.g:3059:3: ( ( ( () 'synchronized' '(' ) )=>this_XSynchronizedExpression_3= ruleXSynchronizedExpression )
                    // InternalDSLPolicies.g:3060:4: ( ( () 'synchronized' '(' ) )=>this_XSynchronizedExpression_3= ruleXSynchronizedExpression
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXSynchronizedExpressionParserRuleCall_3());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_XSynchronizedExpression_3=ruleXSynchronizedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_XSynchronizedExpression_3;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalDSLPolicies.g:3077:3: this_XFeatureCall_4= ruleXFeatureCall
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXFeatureCallParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XFeatureCall_4=ruleXFeatureCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XFeatureCall_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalDSLPolicies.g:3086:3: this_XLiteral_5= ruleXLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXLiteralParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XLiteral_5=ruleXLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XLiteral_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalDSLPolicies.g:3095:3: this_XIfExpression_6= ruleXIfExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXIfExpressionParserRuleCall_6());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XIfExpression_6=ruleXIfExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XIfExpression_6;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalDSLPolicies.g:3104:3: ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=>this_XForLoopExpression_7= ruleXForLoopExpression )
                    {
                    // InternalDSLPolicies.g:3104:3: ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=>this_XForLoopExpression_7= ruleXForLoopExpression )
                    // InternalDSLPolicies.g:3105:4: ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=>this_XForLoopExpression_7= ruleXForLoopExpression
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXForLoopExpressionParserRuleCall_7());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_XForLoopExpression_7=ruleXForLoopExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_XForLoopExpression_7;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 9 :
                    // InternalDSLPolicies.g:3128:3: this_XBasicForLoopExpression_8= ruleXBasicForLoopExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXBasicForLoopExpressionParserRuleCall_8());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XBasicForLoopExpression_8=ruleXBasicForLoopExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XBasicForLoopExpression_8;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalDSLPolicies.g:3137:3: this_XWhileExpression_9= ruleXWhileExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXWhileExpressionParserRuleCall_9());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XWhileExpression_9=ruleXWhileExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XWhileExpression_9;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalDSLPolicies.g:3146:3: this_XDoWhileExpression_10= ruleXDoWhileExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXDoWhileExpressionParserRuleCall_10());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XDoWhileExpression_10=ruleXDoWhileExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XDoWhileExpression_10;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalDSLPolicies.g:3155:3: this_XThrowExpression_11= ruleXThrowExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXThrowExpressionParserRuleCall_11());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XThrowExpression_11=ruleXThrowExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XThrowExpression_11;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalDSLPolicies.g:3164:3: this_XReturnExpression_12= ruleXReturnExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXReturnExpressionParserRuleCall_12());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XReturnExpression_12=ruleXReturnExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XReturnExpression_12;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalDSLPolicies.g:3173:3: this_XTryCatchFinallyExpression_13= ruleXTryCatchFinallyExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXTryCatchFinallyExpressionParserRuleCall_13());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XTryCatchFinallyExpression_13=ruleXTryCatchFinallyExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XTryCatchFinallyExpression_13;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 15 :
                    // InternalDSLPolicies.g:3182:3: this_XParenthesizedExpression_14= ruleXParenthesizedExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXParenthesizedExpressionParserRuleCall_14());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XParenthesizedExpression_14=ruleXParenthesizedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XParenthesizedExpression_14;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXPrimaryExpression"


    // $ANTLR start "entryRuleXLiteral"
    // InternalDSLPolicies.g:3194:1: entryRuleXLiteral returns [EObject current=null] : iv_ruleXLiteral= ruleXLiteral EOF ;
    public final EObject entryRuleXLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXLiteral = null;


        try {
            // InternalDSLPolicies.g:3194:49: (iv_ruleXLiteral= ruleXLiteral EOF )
            // InternalDSLPolicies.g:3195:2: iv_ruleXLiteral= ruleXLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXLiteral=ruleXLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXLiteral"


    // $ANTLR start "ruleXLiteral"
    // InternalDSLPolicies.g:3201:1: ruleXLiteral returns [EObject current=null] : (this_XCollectionLiteral_0= ruleXCollectionLiteral | ( ( ( () '[' ) )=>this_XClosure_1= ruleXClosure ) | this_XBooleanLiteral_2= ruleXBooleanLiteral | this_XNumberLiteral_3= ruleXNumberLiteral | this_XNullLiteral_4= ruleXNullLiteral | this_XStringLiteral_5= ruleXStringLiteral | this_XTypeLiteral_6= ruleXTypeLiteral ) ;
    public final EObject ruleXLiteral() throws RecognitionException {
        EObject current = null;

        EObject this_XCollectionLiteral_0 = null;

        EObject this_XClosure_1 = null;

        EObject this_XBooleanLiteral_2 = null;

        EObject this_XNumberLiteral_3 = null;

        EObject this_XNullLiteral_4 = null;

        EObject this_XStringLiteral_5 = null;

        EObject this_XTypeLiteral_6 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3207:2: ( (this_XCollectionLiteral_0= ruleXCollectionLiteral | ( ( ( () '[' ) )=>this_XClosure_1= ruleXClosure ) | this_XBooleanLiteral_2= ruleXBooleanLiteral | this_XNumberLiteral_3= ruleXNumberLiteral | this_XNullLiteral_4= ruleXNullLiteral | this_XStringLiteral_5= ruleXStringLiteral | this_XTypeLiteral_6= ruleXTypeLiteral ) )
            // InternalDSLPolicies.g:3208:2: (this_XCollectionLiteral_0= ruleXCollectionLiteral | ( ( ( () '[' ) )=>this_XClosure_1= ruleXClosure ) | this_XBooleanLiteral_2= ruleXBooleanLiteral | this_XNumberLiteral_3= ruleXNumberLiteral | this_XNullLiteral_4= ruleXNullLiteral | this_XStringLiteral_5= ruleXStringLiteral | this_XTypeLiteral_6= ruleXTypeLiteral )
            {
            // InternalDSLPolicies.g:3208:2: (this_XCollectionLiteral_0= ruleXCollectionLiteral | ( ( ( () '[' ) )=>this_XClosure_1= ruleXClosure ) | this_XBooleanLiteral_2= ruleXBooleanLiteral | this_XNumberLiteral_3= ruleXNumberLiteral | this_XNullLiteral_4= ruleXNullLiteral | this_XStringLiteral_5= ruleXStringLiteral | this_XTypeLiteral_6= ruleXTypeLiteral )
            int alt50=7;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==71) ) {
                alt50=1;
            }
            else if ( (LA50_0==74) && (synpred21_InternalDSLPolicies())) {
                alt50=2;
            }
            else if ( ((LA50_0>=94 && LA50_0<=95)) ) {
                alt50=3;
            }
            else if ( (LA50_0==RULE_INT||(LA50_0>=RULE_HEX && LA50_0<=RULE_DECIMAL)) ) {
                alt50=4;
            }
            else if ( (LA50_0==96) ) {
                alt50=5;
            }
            else if ( (LA50_0==RULE_STRING) ) {
                alt50=6;
            }
            else if ( (LA50_0==97) ) {
                alt50=7;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }
            switch (alt50) {
                case 1 :
                    // InternalDSLPolicies.g:3209:3: this_XCollectionLiteral_0= ruleXCollectionLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXLiteralAccess().getXCollectionLiteralParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XCollectionLiteral_0=ruleXCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XCollectionLiteral_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:3218:3: ( ( ( () '[' ) )=>this_XClosure_1= ruleXClosure )
                    {
                    // InternalDSLPolicies.g:3218:3: ( ( ( () '[' ) )=>this_XClosure_1= ruleXClosure )
                    // InternalDSLPolicies.g:3219:4: ( ( () '[' ) )=>this_XClosure_1= ruleXClosure
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getXLiteralAccess().getXClosureParserRuleCall_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_XClosure_1=ruleXClosure();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_XClosure_1;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:3235:3: this_XBooleanLiteral_2= ruleXBooleanLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXLiteralAccess().getXBooleanLiteralParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XBooleanLiteral_2=ruleXBooleanLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XBooleanLiteral_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalDSLPolicies.g:3244:3: this_XNumberLiteral_3= ruleXNumberLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXLiteralAccess().getXNumberLiteralParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XNumberLiteral_3=ruleXNumberLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XNumberLiteral_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalDSLPolicies.g:3253:3: this_XNullLiteral_4= ruleXNullLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXLiteralAccess().getXNullLiteralParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XNullLiteral_4=ruleXNullLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XNullLiteral_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalDSLPolicies.g:3262:3: this_XStringLiteral_5= ruleXStringLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXLiteralAccess().getXStringLiteralParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XStringLiteral_5=ruleXStringLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XStringLiteral_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalDSLPolicies.g:3271:3: this_XTypeLiteral_6= ruleXTypeLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXLiteralAccess().getXTypeLiteralParserRuleCall_6());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XTypeLiteral_6=ruleXTypeLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XTypeLiteral_6;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXLiteral"


    // $ANTLR start "entryRuleXCollectionLiteral"
    // InternalDSLPolicies.g:3283:1: entryRuleXCollectionLiteral returns [EObject current=null] : iv_ruleXCollectionLiteral= ruleXCollectionLiteral EOF ;
    public final EObject entryRuleXCollectionLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXCollectionLiteral = null;


        try {
            // InternalDSLPolicies.g:3283:59: (iv_ruleXCollectionLiteral= ruleXCollectionLiteral EOF )
            // InternalDSLPolicies.g:3284:2: iv_ruleXCollectionLiteral= ruleXCollectionLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXCollectionLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXCollectionLiteral=ruleXCollectionLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXCollectionLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXCollectionLiteral"


    // $ANTLR start "ruleXCollectionLiteral"
    // InternalDSLPolicies.g:3290:1: ruleXCollectionLiteral returns [EObject current=null] : (this_XSetLiteral_0= ruleXSetLiteral | this_XListLiteral_1= ruleXListLiteral ) ;
    public final EObject ruleXCollectionLiteral() throws RecognitionException {
        EObject current = null;

        EObject this_XSetLiteral_0 = null;

        EObject this_XListLiteral_1 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3296:2: ( (this_XSetLiteral_0= ruleXSetLiteral | this_XListLiteral_1= ruleXListLiteral ) )
            // InternalDSLPolicies.g:3297:2: (this_XSetLiteral_0= ruleXSetLiteral | this_XListLiteral_1= ruleXListLiteral )
            {
            // InternalDSLPolicies.g:3297:2: (this_XSetLiteral_0= ruleXSetLiteral | this_XListLiteral_1= ruleXListLiteral )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==71) ) {
                int LA51_1 = input.LA(2);

                if ( (LA51_1==74) ) {
                    alt51=2;
                }
                else if ( (LA51_1==72) ) {
                    alt51=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 51, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // InternalDSLPolicies.g:3298:3: this_XSetLiteral_0= ruleXSetLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXCollectionLiteralAccess().getXSetLiteralParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XSetLiteral_0=ruleXSetLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XSetLiteral_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:3307:3: this_XListLiteral_1= ruleXListLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXCollectionLiteralAccess().getXListLiteralParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XListLiteral_1=ruleXListLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XListLiteral_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXCollectionLiteral"


    // $ANTLR start "entryRuleXSetLiteral"
    // InternalDSLPolicies.g:3319:1: entryRuleXSetLiteral returns [EObject current=null] : iv_ruleXSetLiteral= ruleXSetLiteral EOF ;
    public final EObject entryRuleXSetLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXSetLiteral = null;


        try {
            // InternalDSLPolicies.g:3319:52: (iv_ruleXSetLiteral= ruleXSetLiteral EOF )
            // InternalDSLPolicies.g:3320:2: iv_ruleXSetLiteral= ruleXSetLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXSetLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXSetLiteral=ruleXSetLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXSetLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXSetLiteral"


    // $ANTLR start "ruleXSetLiteral"
    // InternalDSLPolicies.g:3326:1: ruleXSetLiteral returns [EObject current=null] : ( () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}' ) ;
    public final EObject ruleXSetLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_elements_3_0 = null;

        EObject lv_elements_5_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3332:2: ( ( () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}' ) )
            // InternalDSLPolicies.g:3333:2: ( () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}' )
            {
            // InternalDSLPolicies.g:3333:2: ( () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}' )
            // InternalDSLPolicies.g:3334:3: () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}'
            {
            // InternalDSLPolicies.g:3334:3: ()
            // InternalDSLPolicies.g:3335:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXSetLiteralAccess().getXSetLiteralAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,71,FOLLOW_48); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXSetLiteralAccess().getNumberSignKeyword_1());
              		
            }
            otherlv_2=(Token)match(input,72,FOLLOW_49); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getXSetLiteralAccess().getLeftCurlyBracketKeyword_2());
              		
            }
            // InternalDSLPolicies.g:3349:3: ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==RULE_ID||(LA53_0>=RULE_INT && LA53_0<=RULE_DECIMAL)||LA53_0==23||LA53_0==41||(LA53_0>=57 && LA53_0<=58)||LA53_0==63||(LA53_0>=71 && LA53_0<=72)||LA53_0==74||LA53_0==77||LA53_0==79||(LA53_0>=83 && LA53_0<=85)||(LA53_0>=88 && LA53_0<=100)||LA53_0==102) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalDSLPolicies.g:3350:4: ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )*
                    {
                    // InternalDSLPolicies.g:3350:4: ( (lv_elements_3_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:3351:5: (lv_elements_3_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:3351:5: (lv_elements_3_0= ruleXExpression )
                    // InternalDSLPolicies.g:3352:6: lv_elements_3_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXSetLiteralAccess().getElementsXExpressionParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_50);
                    lv_elements_3_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXSetLiteralRule());
                      						}
                      						add(
                      							current,
                      							"elements",
                      							lv_elements_3_0,
                      							"org.eclipse.xtext.xbase.Xbase.XExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:3369:4: (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )*
                    loop52:
                    do {
                        int alt52=2;
                        int LA52_0 = input.LA(1);

                        if ( (LA52_0==70) ) {
                            alt52=1;
                        }


                        switch (alt52) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:3370:5: otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,70,FOLLOW_21); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_4, grammarAccess.getXSetLiteralAccess().getCommaKeyword_3_1_0());
                    	      				
                    	    }
                    	    // InternalDSLPolicies.g:3374:5: ( (lv_elements_5_0= ruleXExpression ) )
                    	    // InternalDSLPolicies.g:3375:6: (lv_elements_5_0= ruleXExpression )
                    	    {
                    	    // InternalDSLPolicies.g:3375:6: (lv_elements_5_0= ruleXExpression )
                    	    // InternalDSLPolicies.g:3376:7: lv_elements_5_0= ruleXExpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getXSetLiteralAccess().getElementsXExpressionParserRuleCall_3_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_50);
                    	    lv_elements_5_0=ruleXExpression();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getXSetLiteralRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"elements",
                    	      								lv_elements_5_0,
                    	      								"org.eclipse.xtext.xbase.Xbase.XExpression");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop52;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getXSetLiteralAccess().getRightCurlyBracketKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXSetLiteral"


    // $ANTLR start "entryRuleXListLiteral"
    // InternalDSLPolicies.g:3403:1: entryRuleXListLiteral returns [EObject current=null] : iv_ruleXListLiteral= ruleXListLiteral EOF ;
    public final EObject entryRuleXListLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXListLiteral = null;


        try {
            // InternalDSLPolicies.g:3403:53: (iv_ruleXListLiteral= ruleXListLiteral EOF )
            // InternalDSLPolicies.g:3404:2: iv_ruleXListLiteral= ruleXListLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXListLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXListLiteral=ruleXListLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXListLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXListLiteral"


    // $ANTLR start "ruleXListLiteral"
    // InternalDSLPolicies.g:3410:1: ruleXListLiteral returns [EObject current=null] : ( () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']' ) ;
    public final EObject ruleXListLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_elements_3_0 = null;

        EObject lv_elements_5_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3416:2: ( ( () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']' ) )
            // InternalDSLPolicies.g:3417:2: ( () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']' )
            {
            // InternalDSLPolicies.g:3417:2: ( () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']' )
            // InternalDSLPolicies.g:3418:3: () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']'
            {
            // InternalDSLPolicies.g:3418:3: ()
            // InternalDSLPolicies.g:3419:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXListLiteralAccess().getXListLiteralAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,71,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXListLiteralAccess().getNumberSignKeyword_1());
              		
            }
            otherlv_2=(Token)match(input,74,FOLLOW_52); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getXListLiteralAccess().getLeftSquareBracketKeyword_2());
              		
            }
            // InternalDSLPolicies.g:3433:3: ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==RULE_ID||(LA55_0>=RULE_INT && LA55_0<=RULE_DECIMAL)||LA55_0==23||LA55_0==41||(LA55_0>=57 && LA55_0<=58)||LA55_0==63||(LA55_0>=71 && LA55_0<=72)||LA55_0==74||LA55_0==77||LA55_0==79||(LA55_0>=83 && LA55_0<=85)||(LA55_0>=88 && LA55_0<=100)||LA55_0==102) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalDSLPolicies.g:3434:4: ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )*
                    {
                    // InternalDSLPolicies.g:3434:4: ( (lv_elements_3_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:3435:5: (lv_elements_3_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:3435:5: (lv_elements_3_0= ruleXExpression )
                    // InternalDSLPolicies.g:3436:6: lv_elements_3_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXListLiteralAccess().getElementsXExpressionParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_53);
                    lv_elements_3_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXListLiteralRule());
                      						}
                      						add(
                      							current,
                      							"elements",
                      							lv_elements_3_0,
                      							"org.eclipse.xtext.xbase.Xbase.XExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:3453:4: (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )*
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( (LA54_0==70) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:3454:5: otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,70,FOLLOW_21); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_4, grammarAccess.getXListLiteralAccess().getCommaKeyword_3_1_0());
                    	      				
                    	    }
                    	    // InternalDSLPolicies.g:3458:5: ( (lv_elements_5_0= ruleXExpression ) )
                    	    // InternalDSLPolicies.g:3459:6: (lv_elements_5_0= ruleXExpression )
                    	    {
                    	    // InternalDSLPolicies.g:3459:6: (lv_elements_5_0= ruleXExpression )
                    	    // InternalDSLPolicies.g:3460:7: lv_elements_5_0= ruleXExpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getXListLiteralAccess().getElementsXExpressionParserRuleCall_3_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_53);
                    	    lv_elements_5_0=ruleXExpression();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getXListLiteralRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"elements",
                    	      								lv_elements_5_0,
                    	      								"org.eclipse.xtext.xbase.Xbase.XExpression");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop54;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getXListLiteralAccess().getRightSquareBracketKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXListLiteral"


    // $ANTLR start "entryRuleXClosure"
    // InternalDSLPolicies.g:3487:1: entryRuleXClosure returns [EObject current=null] : iv_ruleXClosure= ruleXClosure EOF ;
    public final EObject entryRuleXClosure() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXClosure = null;


        try {
            // InternalDSLPolicies.g:3487:49: (iv_ruleXClosure= ruleXClosure EOF )
            // InternalDSLPolicies.g:3488:2: iv_ruleXClosure= ruleXClosure EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXClosureRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXClosure=ruleXClosure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXClosure; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXClosure"


    // $ANTLR start "ruleXClosure"
    // InternalDSLPolicies.g:3494:1: ruleXClosure returns [EObject current=null] : ( ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']' ) ;
    public final EObject ruleXClosure() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token lv_explicitSyntax_5_0=null;
        Token otherlv_7=null;
        EObject lv_declaredFormalParameters_2_0 = null;

        EObject lv_declaredFormalParameters_4_0 = null;

        EObject lv_expression_6_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3500:2: ( ( ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']' ) )
            // InternalDSLPolicies.g:3501:2: ( ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']' )
            {
            // InternalDSLPolicies.g:3501:2: ( ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']' )
            // InternalDSLPolicies.g:3502:3: ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']'
            {
            // InternalDSLPolicies.g:3502:3: ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) )
            // InternalDSLPolicies.g:3503:4: ( ( () '[' ) )=> ( () otherlv_1= '[' )
            {
            // InternalDSLPolicies.g:3509:4: ( () otherlv_1= '[' )
            // InternalDSLPolicies.g:3510:5: () otherlv_1= '['
            {
            // InternalDSLPolicies.g:3510:5: ()
            // InternalDSLPolicies.g:3511:6: 
            {
            if ( state.backtracking==0 ) {

              						current = forceCreateModelElement(
              							grammarAccess.getXClosureAccess().getXClosureAction_0_0_0(),
              							current);
              					
            }

            }

            otherlv_1=(Token)match(input,74,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getXClosureAccess().getLeftSquareBracketKeyword_0_0_1());
              				
            }

            }


            }

            // InternalDSLPolicies.g:3523:3: ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )?
            int alt58=2;
            alt58 = dfa58.predict(input);
            switch (alt58) {
                case 1 :
                    // InternalDSLPolicies.g:3524:4: ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) )
                    {
                    // InternalDSLPolicies.g:3547:4: ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) )
                    // InternalDSLPolicies.g:3548:5: ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) )
                    {
                    // InternalDSLPolicies.g:3548:5: ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )?
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==RULE_ID||LA57_0==23||LA57_0==54) ) {
                        alt57=1;
                    }
                    switch (alt57) {
                        case 1 :
                            // InternalDSLPolicies.g:3549:6: ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )*
                            {
                            // InternalDSLPolicies.g:3549:6: ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) )
                            // InternalDSLPolicies.g:3550:7: (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter )
                            {
                            // InternalDSLPolicies.g:3550:7: (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter )
                            // InternalDSLPolicies.g:3551:8: lv_declaredFormalParameters_2_0= ruleJvmFormalParameter
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getXClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_1_0_0_0_0());
                              							
                            }
                            pushFollow(FOLLOW_55);
                            lv_declaredFormalParameters_2_0=ruleJvmFormalParameter();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getXClosureRule());
                              								}
                              								add(
                              									current,
                              									"declaredFormalParameters",
                              									lv_declaredFormalParameters_2_0,
                              									"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            // InternalDSLPolicies.g:3568:6: (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )*
                            loop56:
                            do {
                                int alt56=2;
                                int LA56_0 = input.LA(1);

                                if ( (LA56_0==70) ) {
                                    alt56=1;
                                }


                                switch (alt56) {
                            	case 1 :
                            	    // InternalDSLPolicies.g:3569:7: otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) )
                            	    {
                            	    otherlv_3=(Token)match(input,70,FOLLOW_30); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_3, grammarAccess.getXClosureAccess().getCommaKeyword_1_0_0_1_0());
                            	      						
                            	    }
                            	    // InternalDSLPolicies.g:3573:7: ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) )
                            	    // InternalDSLPolicies.g:3574:8: (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter )
                            	    {
                            	    // InternalDSLPolicies.g:3574:8: (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter )
                            	    // InternalDSLPolicies.g:3575:9: lv_declaredFormalParameters_4_0= ruleJvmFormalParameter
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getXClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_1_0_0_1_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_55);
                            	    lv_declaredFormalParameters_4_0=ruleJvmFormalParameter();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElementForParent(grammarAccess.getXClosureRule());
                            	      									}
                            	      									add(
                            	      										current,
                            	      										"declaredFormalParameters",
                            	      										lv_declaredFormalParameters_4_0,
                            	      										"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
                            	      									afterParserOrEnumRuleCall();
                            	      								
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop56;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalDSLPolicies.g:3594:5: ( (lv_explicitSyntax_5_0= '|' ) )
                    // InternalDSLPolicies.g:3595:6: (lv_explicitSyntax_5_0= '|' )
                    {
                    // InternalDSLPolicies.g:3595:6: (lv_explicitSyntax_5_0= '|' )
                    // InternalDSLPolicies.g:3596:7: lv_explicitSyntax_5_0= '|'
                    {
                    lv_explicitSyntax_5_0=(Token)match(input,76,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_explicitSyntax_5_0, grammarAccess.getXClosureAccess().getExplicitSyntaxVerticalLineKeyword_1_0_1_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getXClosureRule());
                      							}
                      							setWithLastConsumed(current, "explicitSyntax", true, "|");
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalDSLPolicies.g:3610:3: ( (lv_expression_6_0= ruleXExpressionInClosure ) )
            // InternalDSLPolicies.g:3611:4: (lv_expression_6_0= ruleXExpressionInClosure )
            {
            // InternalDSLPolicies.g:3611:4: (lv_expression_6_0= ruleXExpressionInClosure )
            // InternalDSLPolicies.g:3612:5: lv_expression_6_0= ruleXExpressionInClosure
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXClosureAccess().getExpressionXExpressionInClosureParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_57);
            lv_expression_6_0=ruleXExpressionInClosure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXClosureRule());
              					}
              					set(
              						current,
              						"expression",
              						lv_expression_6_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpressionInClosure");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_7=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_7, grammarAccess.getXClosureAccess().getRightSquareBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXClosure"


    // $ANTLR start "entryRuleXExpressionInClosure"
    // InternalDSLPolicies.g:3637:1: entryRuleXExpressionInClosure returns [EObject current=null] : iv_ruleXExpressionInClosure= ruleXExpressionInClosure EOF ;
    public final EObject entryRuleXExpressionInClosure() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXExpressionInClosure = null;


        try {
            // InternalDSLPolicies.g:3637:61: (iv_ruleXExpressionInClosure= ruleXExpressionInClosure EOF )
            // InternalDSLPolicies.g:3638:2: iv_ruleXExpressionInClosure= ruleXExpressionInClosure EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXExpressionInClosureRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXExpressionInClosure=ruleXExpressionInClosure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXExpressionInClosure; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXExpressionInClosure"


    // $ANTLR start "ruleXExpressionInClosure"
    // InternalDSLPolicies.g:3644:1: ruleXExpressionInClosure returns [EObject current=null] : ( () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )* ) ;
    public final EObject ruleXExpressionInClosure() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_expressions_1_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3650:2: ( ( () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )* ) )
            // InternalDSLPolicies.g:3651:2: ( () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )* )
            {
            // InternalDSLPolicies.g:3651:2: ( () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )* )
            // InternalDSLPolicies.g:3652:3: () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )*
            {
            // InternalDSLPolicies.g:3652:3: ()
            // InternalDSLPolicies.g:3653:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXExpressionInClosureAccess().getXBlockExpressionAction_0(),
              					current);
              			
            }

            }

            // InternalDSLPolicies.g:3659:3: ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==RULE_ID||(LA60_0>=RULE_INT && LA60_0<=RULE_DECIMAL)||LA60_0==23||LA60_0==41||(LA60_0>=57 && LA60_0<=58)||LA60_0==63||(LA60_0>=71 && LA60_0<=72)||LA60_0==74||LA60_0==77||LA60_0==79||(LA60_0>=83 && LA60_0<=100)||LA60_0==102) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // InternalDSLPolicies.g:3660:4: ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )?
            	    {
            	    // InternalDSLPolicies.g:3660:4: ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) )
            	    // InternalDSLPolicies.g:3661:5: (lv_expressions_1_0= ruleXExpressionOrVarDeclaration )
            	    {
            	    // InternalDSLPolicies.g:3661:5: (lv_expressions_1_0= ruleXExpressionOrVarDeclaration )
            	    // InternalDSLPolicies.g:3662:6: lv_expressions_1_0= ruleXExpressionOrVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getXExpressionInClosureAccess().getExpressionsXExpressionOrVarDeclarationParserRuleCall_1_0_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_58);
            	    lv_expressions_1_0=ruleXExpressionOrVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getXExpressionInClosureRule());
            	      						}
            	      						add(
            	      							current,
            	      							"expressions",
            	      							lv_expressions_1_0,
            	      							"org.eclipse.xtext.xbase.Xbase.XExpressionOrVarDeclaration");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }

            	    // InternalDSLPolicies.g:3679:4: (otherlv_2= ';' )?
            	    int alt59=2;
            	    int LA59_0 = input.LA(1);

            	    if ( (LA59_0==22) ) {
            	        alt59=1;
            	    }
            	    switch (alt59) {
            	        case 1 :
            	            // InternalDSLPolicies.g:3680:5: otherlv_2= ';'
            	            {
            	            otherlv_2=(Token)match(input,22,FOLLOW_59); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(otherlv_2, grammarAccess.getXExpressionInClosureAccess().getSemicolonKeyword_1_1());
            	              				
            	            }

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXExpressionInClosure"


    // $ANTLR start "entryRuleXShortClosure"
    // InternalDSLPolicies.g:3690:1: entryRuleXShortClosure returns [EObject current=null] : iv_ruleXShortClosure= ruleXShortClosure EOF ;
    public final EObject entryRuleXShortClosure() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXShortClosure = null;


        try {
            // InternalDSLPolicies.g:3690:54: (iv_ruleXShortClosure= ruleXShortClosure EOF )
            // InternalDSLPolicies.g:3691:2: iv_ruleXShortClosure= ruleXShortClosure EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXShortClosureRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXShortClosure=ruleXShortClosure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXShortClosure; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXShortClosure"


    // $ANTLR start "ruleXShortClosure"
    // InternalDSLPolicies.g:3697:1: ruleXShortClosure returns [EObject current=null] : ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) ) ) ;
    public final EObject ruleXShortClosure() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_explicitSyntax_4_0=null;
        EObject lv_declaredFormalParameters_1_0 = null;

        EObject lv_declaredFormalParameters_3_0 = null;

        EObject lv_expression_5_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3703:2: ( ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) ) ) )
            // InternalDSLPolicies.g:3704:2: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) ) )
            {
            // InternalDSLPolicies.g:3704:2: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) ) )
            // InternalDSLPolicies.g:3705:3: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) )
            {
            // InternalDSLPolicies.g:3705:3: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) )
            // InternalDSLPolicies.g:3706:4: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) )
            {
            // InternalDSLPolicies.g:3731:4: ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) )
            // InternalDSLPolicies.g:3732:5: () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) )
            {
            // InternalDSLPolicies.g:3732:5: ()
            // InternalDSLPolicies.g:3733:6: 
            {
            if ( state.backtracking==0 ) {

              						current = forceCreateModelElement(
              							grammarAccess.getXShortClosureAccess().getXClosureAction_0_0_0(),
              							current);
              					
            }

            }

            // InternalDSLPolicies.g:3739:5: ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==RULE_ID||LA62_0==23||LA62_0==54) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalDSLPolicies.g:3740:6: ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )*
                    {
                    // InternalDSLPolicies.g:3740:6: ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) )
                    // InternalDSLPolicies.g:3741:7: (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter )
                    {
                    // InternalDSLPolicies.g:3741:7: (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter )
                    // InternalDSLPolicies.g:3742:8: lv_declaredFormalParameters_1_0= ruleJvmFormalParameter
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getXShortClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_0_0_1_0_0());
                      							
                    }
                    pushFollow(FOLLOW_55);
                    lv_declaredFormalParameters_1_0=ruleJvmFormalParameter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getXShortClosureRule());
                      								}
                      								add(
                      									current,
                      									"declaredFormalParameters",
                      									lv_declaredFormalParameters_1_0,
                      									"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }

                    // InternalDSLPolicies.g:3759:6: (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )*
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( (LA61_0==70) ) {
                            alt61=1;
                        }


                        switch (alt61) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:3760:7: otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) )
                    	    {
                    	    otherlv_2=(Token)match(input,70,FOLLOW_30); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							newLeafNode(otherlv_2, grammarAccess.getXShortClosureAccess().getCommaKeyword_0_0_1_1_0());
                    	      						
                    	    }
                    	    // InternalDSLPolicies.g:3764:7: ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) )
                    	    // InternalDSLPolicies.g:3765:8: (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter )
                    	    {
                    	    // InternalDSLPolicies.g:3765:8: (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter )
                    	    // InternalDSLPolicies.g:3766:9: lv_declaredFormalParameters_3_0= ruleJvmFormalParameter
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      									newCompositeNode(grammarAccess.getXShortClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_0_0_1_1_1_0());
                    	      								
                    	    }
                    	    pushFollow(FOLLOW_55);
                    	    lv_declaredFormalParameters_3_0=ruleJvmFormalParameter();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      									if (current==null) {
                    	      										current = createModelElementForParent(grammarAccess.getXShortClosureRule());
                    	      									}
                    	      									add(
                    	      										current,
                    	      										"declaredFormalParameters",
                    	      										lv_declaredFormalParameters_3_0,
                    	      										"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
                    	      									afterParserOrEnumRuleCall();
                    	      								
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop61;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalDSLPolicies.g:3785:5: ( (lv_explicitSyntax_4_0= '|' ) )
            // InternalDSLPolicies.g:3786:6: (lv_explicitSyntax_4_0= '|' )
            {
            // InternalDSLPolicies.g:3786:6: (lv_explicitSyntax_4_0= '|' )
            // InternalDSLPolicies.g:3787:7: lv_explicitSyntax_4_0= '|'
            {
            lv_explicitSyntax_4_0=(Token)match(input,76,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              							newLeafNode(lv_explicitSyntax_4_0, grammarAccess.getXShortClosureAccess().getExplicitSyntaxVerticalLineKeyword_0_0_2_0());
              						
            }
            if ( state.backtracking==0 ) {

              							if (current==null) {
              								current = createModelElement(grammarAccess.getXShortClosureRule());
              							}
              							setWithLastConsumed(current, "explicitSyntax", true, "|");
              						
            }

            }


            }


            }


            }

            // InternalDSLPolicies.g:3801:3: ( (lv_expression_5_0= ruleXExpression ) )
            // InternalDSLPolicies.g:3802:4: (lv_expression_5_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:3802:4: (lv_expression_5_0= ruleXExpression )
            // InternalDSLPolicies.g:3803:5: lv_expression_5_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXShortClosureAccess().getExpressionXExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_expression_5_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXShortClosureRule());
              					}
              					set(
              						current,
              						"expression",
              						lv_expression_5_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXShortClosure"


    // $ANTLR start "entryRuleXParenthesizedExpression"
    // InternalDSLPolicies.g:3824:1: entryRuleXParenthesizedExpression returns [EObject current=null] : iv_ruleXParenthesizedExpression= ruleXParenthesizedExpression EOF ;
    public final EObject entryRuleXParenthesizedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXParenthesizedExpression = null;


        try {
            // InternalDSLPolicies.g:3824:65: (iv_ruleXParenthesizedExpression= ruleXParenthesizedExpression EOF )
            // InternalDSLPolicies.g:3825:2: iv_ruleXParenthesizedExpression= ruleXParenthesizedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXParenthesizedExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXParenthesizedExpression=ruleXParenthesizedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXParenthesizedExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXParenthesizedExpression"


    // $ANTLR start "ruleXParenthesizedExpression"
    // InternalDSLPolicies.g:3831:1: ruleXParenthesizedExpression returns [EObject current=null] : (otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')' ) ;
    public final EObject ruleXParenthesizedExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_XExpression_1 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3837:2: ( (otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')' ) )
            // InternalDSLPolicies.g:3838:2: (otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')' )
            {
            // InternalDSLPolicies.g:3838:2: (otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')' )
            // InternalDSLPolicies.g:3839:3: otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')'
            {
            otherlv_0=(Token)match(input,23,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getXParenthesizedExpressionAccess().getLeftParenthesisKeyword_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getXParenthesizedExpressionAccess().getXExpressionParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_17);
            this_XExpression_1=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_XExpression_1;
              			afterParserOrEnumRuleCall();
              		
            }
            otherlv_2=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getXParenthesizedExpressionAccess().getRightParenthesisKeyword_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXParenthesizedExpression"


    // $ANTLR start "entryRuleXIfExpression"
    // InternalDSLPolicies.g:3859:1: entryRuleXIfExpression returns [EObject current=null] : iv_ruleXIfExpression= ruleXIfExpression EOF ;
    public final EObject entryRuleXIfExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXIfExpression = null;


        try {
            // InternalDSLPolicies.g:3859:54: (iv_ruleXIfExpression= ruleXIfExpression EOF )
            // InternalDSLPolicies.g:3860:2: iv_ruleXIfExpression= ruleXIfExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXIfExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXIfExpression=ruleXIfExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXIfExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXIfExpression"


    // $ANTLR start "ruleXIfExpression"
    // InternalDSLPolicies.g:3866:1: ruleXIfExpression returns [EObject current=null] : ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )? ) ;
    public final EObject ruleXIfExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_if_3_0 = null;

        EObject lv_then_5_0 = null;

        EObject lv_else_7_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3872:2: ( ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )? ) )
            // InternalDSLPolicies.g:3873:2: ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )? )
            {
            // InternalDSLPolicies.g:3873:2: ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )? )
            // InternalDSLPolicies.g:3874:3: () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )?
            {
            // InternalDSLPolicies.g:3874:3: ()
            // InternalDSLPolicies.g:3875:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXIfExpressionAccess().getXIfExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,77,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXIfExpressionAccess().getIfKeyword_1());
              		
            }
            otherlv_2=(Token)match(input,23,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getXIfExpressionAccess().getLeftParenthesisKeyword_2());
              		
            }
            // InternalDSLPolicies.g:3889:3: ( (lv_if_3_0= ruleXExpression ) )
            // InternalDSLPolicies.g:3890:4: (lv_if_3_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:3890:4: (lv_if_3_0= ruleXExpression )
            // InternalDSLPolicies.g:3891:5: lv_if_3_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXIfExpressionAccess().getIfXExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_if_3_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXIfExpressionRule());
              					}
              					set(
              						current,
              						"if",
              						lv_if_3_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_4=(Token)match(input,24,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getXIfExpressionAccess().getRightParenthesisKeyword_4());
              		
            }
            // InternalDSLPolicies.g:3912:3: ( (lv_then_5_0= ruleXExpression ) )
            // InternalDSLPolicies.g:3913:4: (lv_then_5_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:3913:4: (lv_then_5_0= ruleXExpression )
            // InternalDSLPolicies.g:3914:5: lv_then_5_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXIfExpressionAccess().getThenXExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_60);
            lv_then_5_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXIfExpressionRule());
              					}
              					set(
              						current,
              						"then",
              						lv_then_5_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalDSLPolicies.g:3931:3: ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==78) ) {
                int LA63_1 = input.LA(2);

                if ( (synpred25_InternalDSLPolicies()) ) {
                    alt63=1;
                }
            }
            switch (alt63) {
                case 1 :
                    // InternalDSLPolicies.g:3932:4: ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) )
                    {
                    // InternalDSLPolicies.g:3932:4: ( ( 'else' )=>otherlv_6= 'else' )
                    // InternalDSLPolicies.g:3933:5: ( 'else' )=>otherlv_6= 'else'
                    {
                    otherlv_6=(Token)match(input,78,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_6, grammarAccess.getXIfExpressionAccess().getElseKeyword_6_0());
                      				
                    }

                    }

                    // InternalDSLPolicies.g:3939:4: ( (lv_else_7_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:3940:5: (lv_else_7_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:3940:5: (lv_else_7_0= ruleXExpression )
                    // InternalDSLPolicies.g:3941:6: lv_else_7_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXIfExpressionAccess().getElseXExpressionParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_else_7_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXIfExpressionRule());
                      						}
                      						set(
                      							current,
                      							"else",
                      							lv_else_7_0,
                      							"org.eclipse.xtext.xbase.Xbase.XExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXIfExpression"


    // $ANTLR start "entryRuleXSwitchExpression"
    // InternalDSLPolicies.g:3963:1: entryRuleXSwitchExpression returns [EObject current=null] : iv_ruleXSwitchExpression= ruleXSwitchExpression EOF ;
    public final EObject entryRuleXSwitchExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXSwitchExpression = null;


        try {
            // InternalDSLPolicies.g:3963:58: (iv_ruleXSwitchExpression= ruleXSwitchExpression EOF )
            // InternalDSLPolicies.g:3964:2: iv_ruleXSwitchExpression= ruleXSwitchExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXSwitchExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXSwitchExpression=ruleXSwitchExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXSwitchExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXSwitchExpression"


    // $ANTLR start "ruleXSwitchExpression"
    // InternalDSLPolicies.g:3970:1: ruleXSwitchExpression returns [EObject current=null] : ( () otherlv_1= 'switch' ( ( ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')' ) | ( ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) ) ) ) otherlv_10= '{' ( (lv_cases_11_0= ruleXCasePart ) )* (otherlv_12= 'default' otherlv_13= ':' ( (lv_default_14_0= ruleXExpression ) ) )? otherlv_15= '}' ) ;
    public final EObject ruleXSwitchExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        EObject lv_declaredParam_3_0 = null;

        EObject lv_switch_5_0 = null;

        EObject lv_declaredParam_7_0 = null;

        EObject lv_switch_9_0 = null;

        EObject lv_cases_11_0 = null;

        EObject lv_default_14_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:3976:2: ( ( () otherlv_1= 'switch' ( ( ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')' ) | ( ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) ) ) ) otherlv_10= '{' ( (lv_cases_11_0= ruleXCasePart ) )* (otherlv_12= 'default' otherlv_13= ':' ( (lv_default_14_0= ruleXExpression ) ) )? otherlv_15= '}' ) )
            // InternalDSLPolicies.g:3977:2: ( () otherlv_1= 'switch' ( ( ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')' ) | ( ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) ) ) ) otherlv_10= '{' ( (lv_cases_11_0= ruleXCasePart ) )* (otherlv_12= 'default' otherlv_13= ':' ( (lv_default_14_0= ruleXExpression ) ) )? otherlv_15= '}' )
            {
            // InternalDSLPolicies.g:3977:2: ( () otherlv_1= 'switch' ( ( ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')' ) | ( ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) ) ) ) otherlv_10= '{' ( (lv_cases_11_0= ruleXCasePart ) )* (otherlv_12= 'default' otherlv_13= ':' ( (lv_default_14_0= ruleXExpression ) ) )? otherlv_15= '}' )
            // InternalDSLPolicies.g:3978:3: () otherlv_1= 'switch' ( ( ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')' ) | ( ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) ) ) ) otherlv_10= '{' ( (lv_cases_11_0= ruleXCasePart ) )* (otherlv_12= 'default' otherlv_13= ':' ( (lv_default_14_0= ruleXExpression ) ) )? otherlv_15= '}'
            {
            // InternalDSLPolicies.g:3978:3: ()
            // InternalDSLPolicies.g:3979:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXSwitchExpressionAccess().getXSwitchExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,79,FOLLOW_61); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXSwitchExpressionAccess().getSwitchKeyword_1());
              		
            }
            // InternalDSLPolicies.g:3989:3: ( ( ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')' ) | ( ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) ) ) )
            int alt65=2;
            alt65 = dfa65.predict(input);
            switch (alt65) {
                case 1 :
                    // InternalDSLPolicies.g:3990:4: ( ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')' )
                    {
                    // InternalDSLPolicies.g:3990:4: ( ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')' )
                    // InternalDSLPolicies.g:3991:5: ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')'
                    {
                    // InternalDSLPolicies.g:3991:5: ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) )
                    // InternalDSLPolicies.g:3992:6: ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' )
                    {
                    // InternalDSLPolicies.g:4002:6: (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' )
                    // InternalDSLPolicies.g:4003:7: otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':'
                    {
                    otherlv_2=(Token)match(input,23,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(otherlv_2, grammarAccess.getXSwitchExpressionAccess().getLeftParenthesisKeyword_2_0_0_0_0());
                      						
                    }
                    // InternalDSLPolicies.g:4007:7: ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) )
                    // InternalDSLPolicies.g:4008:8: (lv_declaredParam_3_0= ruleJvmFormalParameter )
                    {
                    // InternalDSLPolicies.g:4008:8: (lv_declaredParam_3_0= ruleJvmFormalParameter )
                    // InternalDSLPolicies.g:4009:9: lv_declaredParam_3_0= ruleJvmFormalParameter
                    {
                    if ( state.backtracking==0 ) {

                      									newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getDeclaredParamJvmFormalParameterParserRuleCall_2_0_0_0_1_0());
                      								
                    }
                    pushFollow(FOLLOW_62);
                    lv_declaredParam_3_0=ruleJvmFormalParameter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      									if (current==null) {
                      										current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
                      									}
                      									set(
                      										current,
                      										"declaredParam",
                      										lv_declaredParam_3_0,
                      										"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
                      									afterParserOrEnumRuleCall();
                      								
                    }

                    }


                    }

                    otherlv_4=(Token)match(input,80,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(otherlv_4, grammarAccess.getXSwitchExpressionAccess().getColonKeyword_2_0_0_0_2());
                      						
                    }

                    }


                    }

                    // InternalDSLPolicies.g:4032:5: ( (lv_switch_5_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:4033:6: (lv_switch_5_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:4033:6: (lv_switch_5_0= ruleXExpression )
                    // InternalDSLPolicies.g:4034:7: lv_switch_5_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getSwitchXExpressionParserRuleCall_2_0_1_0());
                      						
                    }
                    pushFollow(FOLLOW_17);
                    lv_switch_5_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
                      							}
                      							set(
                      								current,
                      								"switch",
                      								lv_switch_5_0,
                      								"org.eclipse.xtext.xbase.Xbase.XExpression");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    otherlv_6=(Token)match(input,24,FOLLOW_48); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_6, grammarAccess.getXSwitchExpressionAccess().getRightParenthesisKeyword_2_0_2());
                      				
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:4057:4: ( ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) ) )
                    {
                    // InternalDSLPolicies.g:4057:4: ( ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) ) )
                    // InternalDSLPolicies.g:4058:5: ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) )
                    {
                    // InternalDSLPolicies.g:4058:5: ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )?
                    int alt64=2;
                    alt64 = dfa64.predict(input);
                    switch (alt64) {
                        case 1 :
                            // InternalDSLPolicies.g:4059:6: ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' )
                            {
                            // InternalDSLPolicies.g:4068:6: ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' )
                            // InternalDSLPolicies.g:4069:7: ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':'
                            {
                            // InternalDSLPolicies.g:4069:7: ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) )
                            // InternalDSLPolicies.g:4070:8: (lv_declaredParam_7_0= ruleJvmFormalParameter )
                            {
                            // InternalDSLPolicies.g:4070:8: (lv_declaredParam_7_0= ruleJvmFormalParameter )
                            // InternalDSLPolicies.g:4071:9: lv_declaredParam_7_0= ruleJvmFormalParameter
                            {
                            if ( state.backtracking==0 ) {

                              									newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getDeclaredParamJvmFormalParameterParserRuleCall_2_1_0_0_0_0());
                              								
                            }
                            pushFollow(FOLLOW_62);
                            lv_declaredParam_7_0=ruleJvmFormalParameter();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              									if (current==null) {
                              										current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
                              									}
                              									set(
                              										current,
                              										"declaredParam",
                              										lv_declaredParam_7_0,
                              										"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
                              									afterParserOrEnumRuleCall();
                              								
                            }

                            }


                            }

                            otherlv_8=(Token)match(input,80,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(otherlv_8, grammarAccess.getXSwitchExpressionAccess().getColonKeyword_2_1_0_0_1());
                              						
                            }

                            }


                            }
                            break;

                    }

                    // InternalDSLPolicies.g:4094:5: ( (lv_switch_9_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:4095:6: (lv_switch_9_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:4095:6: (lv_switch_9_0= ruleXExpression )
                    // InternalDSLPolicies.g:4096:7: lv_switch_9_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getSwitchXExpressionParserRuleCall_2_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_48);
                    lv_switch_9_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
                      							}
                      							set(
                      								current,
                      								"switch",
                      								lv_switch_9_0,
                      								"org.eclipse.xtext.xbase.Xbase.XExpression");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }

            otherlv_10=(Token)match(input,72,FOLLOW_63); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_10, grammarAccess.getXSwitchExpressionAccess().getLeftCurlyBracketKeyword_3());
              		
            }
            // InternalDSLPolicies.g:4119:3: ( (lv_cases_11_0= ruleXCasePart ) )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==RULE_ID||LA66_0==23||LA66_0==54||LA66_0==70||LA66_0==80||LA66_0==82) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // InternalDSLPolicies.g:4120:4: (lv_cases_11_0= ruleXCasePart )
            	    {
            	    // InternalDSLPolicies.g:4120:4: (lv_cases_11_0= ruleXCasePart )
            	    // InternalDSLPolicies.g:4121:5: lv_cases_11_0= ruleXCasePart
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getCasesXCasePartParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_63);
            	    lv_cases_11_0=ruleXCasePart();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"cases",
            	      						lv_cases_11_0,
            	      						"org.eclipse.xtext.xbase.Xbase.XCasePart");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop66;
                }
            } while (true);

            // InternalDSLPolicies.g:4138:3: (otherlv_12= 'default' otherlv_13= ':' ( (lv_default_14_0= ruleXExpression ) ) )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==81) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalDSLPolicies.g:4139:4: otherlv_12= 'default' otherlv_13= ':' ( (lv_default_14_0= ruleXExpression ) )
                    {
                    otherlv_12=(Token)match(input,81,FOLLOW_62); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getXSwitchExpressionAccess().getDefaultKeyword_5_0());
                      			
                    }
                    otherlv_13=(Token)match(input,80,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getXSwitchExpressionAccess().getColonKeyword_5_1());
                      			
                    }
                    // InternalDSLPolicies.g:4147:4: ( (lv_default_14_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:4148:5: (lv_default_14_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:4148:5: (lv_default_14_0= ruleXExpression )
                    // InternalDSLPolicies.g:4149:6: lv_default_14_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getDefaultXExpressionParserRuleCall_5_2_0());
                      					
                    }
                    pushFollow(FOLLOW_64);
                    lv_default_14_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
                      						}
                      						set(
                      							current,
                      							"default",
                      							lv_default_14_0,
                      							"org.eclipse.xtext.xbase.Xbase.XExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_15=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_15, grammarAccess.getXSwitchExpressionAccess().getRightCurlyBracketKeyword_6());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXSwitchExpression"


    // $ANTLR start "entryRuleXCasePart"
    // InternalDSLPolicies.g:4175:1: entryRuleXCasePart returns [EObject current=null] : iv_ruleXCasePart= ruleXCasePart EOF ;
    public final EObject entryRuleXCasePart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXCasePart = null;


        try {
            // InternalDSLPolicies.g:4175:50: (iv_ruleXCasePart= ruleXCasePart EOF )
            // InternalDSLPolicies.g:4176:2: iv_ruleXCasePart= ruleXCasePart EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXCasePartRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXCasePart=ruleXCasePart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXCasePart; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXCasePart"


    // $ANTLR start "ruleXCasePart"
    // InternalDSLPolicies.g:4182:1: ruleXCasePart returns [EObject current=null] : ( () ( (lv_typeGuard_1_0= ruleJvmTypeReference ) )? (otherlv_2= 'case' ( (lv_case_3_0= ruleXExpression ) ) )? ( (otherlv_4= ':' ( (lv_then_5_0= ruleXExpression ) ) ) | ( (lv_fallThrough_6_0= ',' ) ) ) ) ;
    public final EObject ruleXCasePart() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_fallThrough_6_0=null;
        EObject lv_typeGuard_1_0 = null;

        EObject lv_case_3_0 = null;

        EObject lv_then_5_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:4188:2: ( ( () ( (lv_typeGuard_1_0= ruleJvmTypeReference ) )? (otherlv_2= 'case' ( (lv_case_3_0= ruleXExpression ) ) )? ( (otherlv_4= ':' ( (lv_then_5_0= ruleXExpression ) ) ) | ( (lv_fallThrough_6_0= ',' ) ) ) ) )
            // InternalDSLPolicies.g:4189:2: ( () ( (lv_typeGuard_1_0= ruleJvmTypeReference ) )? (otherlv_2= 'case' ( (lv_case_3_0= ruleXExpression ) ) )? ( (otherlv_4= ':' ( (lv_then_5_0= ruleXExpression ) ) ) | ( (lv_fallThrough_6_0= ',' ) ) ) )
            {
            // InternalDSLPolicies.g:4189:2: ( () ( (lv_typeGuard_1_0= ruleJvmTypeReference ) )? (otherlv_2= 'case' ( (lv_case_3_0= ruleXExpression ) ) )? ( (otherlv_4= ':' ( (lv_then_5_0= ruleXExpression ) ) ) | ( (lv_fallThrough_6_0= ',' ) ) ) )
            // InternalDSLPolicies.g:4190:3: () ( (lv_typeGuard_1_0= ruleJvmTypeReference ) )? (otherlv_2= 'case' ( (lv_case_3_0= ruleXExpression ) ) )? ( (otherlv_4= ':' ( (lv_then_5_0= ruleXExpression ) ) ) | ( (lv_fallThrough_6_0= ',' ) ) )
            {
            // InternalDSLPolicies.g:4190:3: ()
            // InternalDSLPolicies.g:4191:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXCasePartAccess().getXCasePartAction_0(),
              					current);
              			
            }

            }

            // InternalDSLPolicies.g:4197:3: ( (lv_typeGuard_1_0= ruleJvmTypeReference ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==RULE_ID||LA68_0==23||LA68_0==54) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalDSLPolicies.g:4198:4: (lv_typeGuard_1_0= ruleJvmTypeReference )
                    {
                    // InternalDSLPolicies.g:4198:4: (lv_typeGuard_1_0= ruleJvmTypeReference )
                    // InternalDSLPolicies.g:4199:5: lv_typeGuard_1_0= ruleJvmTypeReference
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getXCasePartAccess().getTypeGuardJvmTypeReferenceParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_65);
                    lv_typeGuard_1_0=ruleJvmTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getXCasePartRule());
                      					}
                      					set(
                      						current,
                      						"typeGuard",
                      						lv_typeGuard_1_0,
                      						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalDSLPolicies.g:4216:3: (otherlv_2= 'case' ( (lv_case_3_0= ruleXExpression ) ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==82) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalDSLPolicies.g:4217:4: otherlv_2= 'case' ( (lv_case_3_0= ruleXExpression ) )
                    {
                    otherlv_2=(Token)match(input,82,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getXCasePartAccess().getCaseKeyword_2_0());
                      			
                    }
                    // InternalDSLPolicies.g:4221:4: ( (lv_case_3_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:4222:5: (lv_case_3_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:4222:5: (lv_case_3_0= ruleXExpression )
                    // InternalDSLPolicies.g:4223:6: lv_case_3_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXCasePartAccess().getCaseXExpressionParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_66);
                    lv_case_3_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXCasePartRule());
                      						}
                      						set(
                      							current,
                      							"case",
                      							lv_case_3_0,
                      							"org.eclipse.xtext.xbase.Xbase.XExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalDSLPolicies.g:4241:3: ( (otherlv_4= ':' ( (lv_then_5_0= ruleXExpression ) ) ) | ( (lv_fallThrough_6_0= ',' ) ) )
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==80) ) {
                alt70=1;
            }
            else if ( (LA70_0==70) ) {
                alt70=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    // InternalDSLPolicies.g:4242:4: (otherlv_4= ':' ( (lv_then_5_0= ruleXExpression ) ) )
                    {
                    // InternalDSLPolicies.g:4242:4: (otherlv_4= ':' ( (lv_then_5_0= ruleXExpression ) ) )
                    // InternalDSLPolicies.g:4243:5: otherlv_4= ':' ( (lv_then_5_0= ruleXExpression ) )
                    {
                    otherlv_4=(Token)match(input,80,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_4, grammarAccess.getXCasePartAccess().getColonKeyword_3_0_0());
                      				
                    }
                    // InternalDSLPolicies.g:4247:5: ( (lv_then_5_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:4248:6: (lv_then_5_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:4248:6: (lv_then_5_0= ruleXExpression )
                    // InternalDSLPolicies.g:4249:7: lv_then_5_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getXCasePartAccess().getThenXExpressionParserRuleCall_3_0_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_then_5_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getXCasePartRule());
                      							}
                      							set(
                      								current,
                      								"then",
                      								lv_then_5_0,
                      								"org.eclipse.xtext.xbase.Xbase.XExpression");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:4268:4: ( (lv_fallThrough_6_0= ',' ) )
                    {
                    // InternalDSLPolicies.g:4268:4: ( (lv_fallThrough_6_0= ',' ) )
                    // InternalDSLPolicies.g:4269:5: (lv_fallThrough_6_0= ',' )
                    {
                    // InternalDSLPolicies.g:4269:5: (lv_fallThrough_6_0= ',' )
                    // InternalDSLPolicies.g:4270:6: lv_fallThrough_6_0= ','
                    {
                    lv_fallThrough_6_0=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_fallThrough_6_0, grammarAccess.getXCasePartAccess().getFallThroughCommaKeyword_3_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getXCasePartRule());
                      						}
                      						setWithLastConsumed(current, "fallThrough", true, ",");
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXCasePart"


    // $ANTLR start "entryRuleXForLoopExpression"
    // InternalDSLPolicies.g:4287:1: entryRuleXForLoopExpression returns [EObject current=null] : iv_ruleXForLoopExpression= ruleXForLoopExpression EOF ;
    public final EObject entryRuleXForLoopExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXForLoopExpression = null;


        try {
            // InternalDSLPolicies.g:4287:59: (iv_ruleXForLoopExpression= ruleXForLoopExpression EOF )
            // InternalDSLPolicies.g:4288:2: iv_ruleXForLoopExpression= ruleXForLoopExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXForLoopExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXForLoopExpression=ruleXForLoopExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXForLoopExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXForLoopExpression"


    // $ANTLR start "ruleXForLoopExpression"
    // InternalDSLPolicies.g:4294:1: ruleXForLoopExpression returns [EObject current=null] : ( ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_forExpression_5_0= ruleXExpression ) ) otherlv_6= ')' ( (lv_eachExpression_7_0= ruleXExpression ) ) ) ;
    public final EObject ruleXForLoopExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_declaredParam_3_0 = null;

        EObject lv_forExpression_5_0 = null;

        EObject lv_eachExpression_7_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:4300:2: ( ( ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_forExpression_5_0= ruleXExpression ) ) otherlv_6= ')' ( (lv_eachExpression_7_0= ruleXExpression ) ) ) )
            // InternalDSLPolicies.g:4301:2: ( ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_forExpression_5_0= ruleXExpression ) ) otherlv_6= ')' ( (lv_eachExpression_7_0= ruleXExpression ) ) )
            {
            // InternalDSLPolicies.g:4301:2: ( ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_forExpression_5_0= ruleXExpression ) ) otherlv_6= ')' ( (lv_eachExpression_7_0= ruleXExpression ) ) )
            // InternalDSLPolicies.g:4302:3: ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_forExpression_5_0= ruleXExpression ) ) otherlv_6= ')' ( (lv_eachExpression_7_0= ruleXExpression ) )
            {
            // InternalDSLPolicies.g:4302:3: ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) )
            // InternalDSLPolicies.g:4303:4: ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' )
            {
            // InternalDSLPolicies.g:4316:4: ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' )
            // InternalDSLPolicies.g:4317:5: () otherlv_1= 'for' otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':'
            {
            // InternalDSLPolicies.g:4317:5: ()
            // InternalDSLPolicies.g:4318:6: 
            {
            if ( state.backtracking==0 ) {

              						current = forceCreateModelElement(
              							grammarAccess.getXForLoopExpressionAccess().getXForLoopExpressionAction_0_0_0(),
              							current);
              					
            }

            }

            otherlv_1=(Token)match(input,83,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getXForLoopExpressionAccess().getForKeyword_0_0_1());
              				
            }
            otherlv_2=(Token)match(input,23,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_2, grammarAccess.getXForLoopExpressionAccess().getLeftParenthesisKeyword_0_0_2());
              				
            }
            // InternalDSLPolicies.g:4332:5: ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) )
            // InternalDSLPolicies.g:4333:6: (lv_declaredParam_3_0= ruleJvmFormalParameter )
            {
            // InternalDSLPolicies.g:4333:6: (lv_declaredParam_3_0= ruleJvmFormalParameter )
            // InternalDSLPolicies.g:4334:7: lv_declaredParam_3_0= ruleJvmFormalParameter
            {
            if ( state.backtracking==0 ) {

              							newCompositeNode(grammarAccess.getXForLoopExpressionAccess().getDeclaredParamJvmFormalParameterParserRuleCall_0_0_3_0());
              						
            }
            pushFollow(FOLLOW_62);
            lv_declaredParam_3_0=ruleJvmFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              							if (current==null) {
              								current = createModelElementForParent(grammarAccess.getXForLoopExpressionRule());
              							}
              							set(
              								current,
              								"declaredParam",
              								lv_declaredParam_3_0,
              								"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
              							afterParserOrEnumRuleCall();
              						
            }

            }


            }

            otherlv_4=(Token)match(input,80,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_4, grammarAccess.getXForLoopExpressionAccess().getColonKeyword_0_0_4());
              				
            }

            }


            }

            // InternalDSLPolicies.g:4357:3: ( (lv_forExpression_5_0= ruleXExpression ) )
            // InternalDSLPolicies.g:4358:4: (lv_forExpression_5_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:4358:4: (lv_forExpression_5_0= ruleXExpression )
            // InternalDSLPolicies.g:4359:5: lv_forExpression_5_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXForLoopExpressionAccess().getForExpressionXExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_forExpression_5_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXForLoopExpressionRule());
              					}
              					set(
              						current,
              						"forExpression",
              						lv_forExpression_5_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_6=(Token)match(input,24,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getXForLoopExpressionAccess().getRightParenthesisKeyword_2());
              		
            }
            // InternalDSLPolicies.g:4380:3: ( (lv_eachExpression_7_0= ruleXExpression ) )
            // InternalDSLPolicies.g:4381:4: (lv_eachExpression_7_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:4381:4: (lv_eachExpression_7_0= ruleXExpression )
            // InternalDSLPolicies.g:4382:5: lv_eachExpression_7_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXForLoopExpressionAccess().getEachExpressionXExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_eachExpression_7_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXForLoopExpressionRule());
              					}
              					set(
              						current,
              						"eachExpression",
              						lv_eachExpression_7_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXForLoopExpression"


    // $ANTLR start "entryRuleXBasicForLoopExpression"
    // InternalDSLPolicies.g:4403:1: entryRuleXBasicForLoopExpression returns [EObject current=null] : iv_ruleXBasicForLoopExpression= ruleXBasicForLoopExpression EOF ;
    public final EObject entryRuleXBasicForLoopExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXBasicForLoopExpression = null;


        try {
            // InternalDSLPolicies.g:4403:64: (iv_ruleXBasicForLoopExpression= ruleXBasicForLoopExpression EOF )
            // InternalDSLPolicies.g:4404:2: iv_ruleXBasicForLoopExpression= ruleXBasicForLoopExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXBasicForLoopExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXBasicForLoopExpression=ruleXBasicForLoopExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXBasicForLoopExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXBasicForLoopExpression"


    // $ANTLR start "ruleXBasicForLoopExpression"
    // InternalDSLPolicies.g:4410:1: ruleXBasicForLoopExpression returns [EObject current=null] : ( () otherlv_1= 'for' otherlv_2= '(' ( ( (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_4= ',' ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) ) )* )? otherlv_6= ';' ( (lv_expression_7_0= ruleXExpression ) )? otherlv_8= ';' ( ( (lv_updateExpressions_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_updateExpressions_11_0= ruleXExpression ) ) )* )? otherlv_12= ')' ( (lv_eachExpression_13_0= ruleXExpression ) ) ) ;
    public final EObject ruleXBasicForLoopExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        EObject lv_initExpressions_3_0 = null;

        EObject lv_initExpressions_5_0 = null;

        EObject lv_expression_7_0 = null;

        EObject lv_updateExpressions_9_0 = null;

        EObject lv_updateExpressions_11_0 = null;

        EObject lv_eachExpression_13_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:4416:2: ( ( () otherlv_1= 'for' otherlv_2= '(' ( ( (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_4= ',' ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) ) )* )? otherlv_6= ';' ( (lv_expression_7_0= ruleXExpression ) )? otherlv_8= ';' ( ( (lv_updateExpressions_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_updateExpressions_11_0= ruleXExpression ) ) )* )? otherlv_12= ')' ( (lv_eachExpression_13_0= ruleXExpression ) ) ) )
            // InternalDSLPolicies.g:4417:2: ( () otherlv_1= 'for' otherlv_2= '(' ( ( (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_4= ',' ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) ) )* )? otherlv_6= ';' ( (lv_expression_7_0= ruleXExpression ) )? otherlv_8= ';' ( ( (lv_updateExpressions_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_updateExpressions_11_0= ruleXExpression ) ) )* )? otherlv_12= ')' ( (lv_eachExpression_13_0= ruleXExpression ) ) )
            {
            // InternalDSLPolicies.g:4417:2: ( () otherlv_1= 'for' otherlv_2= '(' ( ( (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_4= ',' ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) ) )* )? otherlv_6= ';' ( (lv_expression_7_0= ruleXExpression ) )? otherlv_8= ';' ( ( (lv_updateExpressions_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_updateExpressions_11_0= ruleXExpression ) ) )* )? otherlv_12= ')' ( (lv_eachExpression_13_0= ruleXExpression ) ) )
            // InternalDSLPolicies.g:4418:3: () otherlv_1= 'for' otherlv_2= '(' ( ( (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_4= ',' ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) ) )* )? otherlv_6= ';' ( (lv_expression_7_0= ruleXExpression ) )? otherlv_8= ';' ( ( (lv_updateExpressions_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_updateExpressions_11_0= ruleXExpression ) ) )* )? otherlv_12= ')' ( (lv_eachExpression_13_0= ruleXExpression ) )
            {
            // InternalDSLPolicies.g:4418:3: ()
            // InternalDSLPolicies.g:4419:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXBasicForLoopExpressionAccess().getXBasicForLoopExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,83,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXBasicForLoopExpressionAccess().getForKeyword_1());
              		
            }
            otherlv_2=(Token)match(input,23,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getXBasicForLoopExpressionAccess().getLeftParenthesisKeyword_2());
              		
            }
            // InternalDSLPolicies.g:4433:3: ( ( (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_4= ',' ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) ) )* )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==RULE_ID||(LA72_0>=RULE_INT && LA72_0<=RULE_DECIMAL)||LA72_0==23||LA72_0==41||(LA72_0>=57 && LA72_0<=58)||LA72_0==63||(LA72_0>=71 && LA72_0<=72)||LA72_0==74||LA72_0==77||LA72_0==79||(LA72_0>=83 && LA72_0<=100)||LA72_0==102) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // InternalDSLPolicies.g:4434:4: ( (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_4= ',' ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) ) )*
                    {
                    // InternalDSLPolicies.g:4434:4: ( (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration ) )
                    // InternalDSLPolicies.g:4435:5: (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration )
                    {
                    // InternalDSLPolicies.g:4435:5: (lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration )
                    // InternalDSLPolicies.g:4436:6: lv_initExpressions_3_0= ruleXExpressionOrVarDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getInitExpressionsXExpressionOrVarDeclarationParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_68);
                    lv_initExpressions_3_0=ruleXExpressionOrVarDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
                      						}
                      						add(
                      							current,
                      							"initExpressions",
                      							lv_initExpressions_3_0,
                      							"org.eclipse.xtext.xbase.Xbase.XExpressionOrVarDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:4453:4: (otherlv_4= ',' ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) ) )*
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( (LA71_0==70) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:4454:5: otherlv_4= ',' ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) )
                    	    {
                    	    otherlv_4=(Token)match(input,70,FOLLOW_69); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_4, grammarAccess.getXBasicForLoopExpressionAccess().getCommaKeyword_3_1_0());
                    	      				
                    	    }
                    	    // InternalDSLPolicies.g:4458:5: ( (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration ) )
                    	    // InternalDSLPolicies.g:4459:6: (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration )
                    	    {
                    	    // InternalDSLPolicies.g:4459:6: (lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration )
                    	    // InternalDSLPolicies.g:4460:7: lv_initExpressions_5_0= ruleXExpressionOrVarDeclaration
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getInitExpressionsXExpressionOrVarDeclarationParserRuleCall_3_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_68);
                    	    lv_initExpressions_5_0=ruleXExpressionOrVarDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"initExpressions",
                    	      								lv_initExpressions_5_0,
                    	      								"org.eclipse.xtext.xbase.Xbase.XExpressionOrVarDeclaration");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop71;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,22,FOLLOW_70); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getXBasicForLoopExpressionAccess().getSemicolonKeyword_4());
              		
            }
            // InternalDSLPolicies.g:4483:3: ( (lv_expression_7_0= ruleXExpression ) )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==RULE_ID||(LA73_0>=RULE_INT && LA73_0<=RULE_DECIMAL)||LA73_0==23||LA73_0==41||(LA73_0>=57 && LA73_0<=58)||LA73_0==63||(LA73_0>=71 && LA73_0<=72)||LA73_0==74||LA73_0==77||LA73_0==79||(LA73_0>=83 && LA73_0<=85)||(LA73_0>=88 && LA73_0<=100)||LA73_0==102) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalDSLPolicies.g:4484:4: (lv_expression_7_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:4484:4: (lv_expression_7_0= ruleXExpression )
                    // InternalDSLPolicies.g:4485:5: lv_expression_7_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getExpressionXExpressionParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_14);
                    lv_expression_7_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
                      					}
                      					set(
                      						current,
                      						"expression",
                      						lv_expression_7_0,
                      						"org.eclipse.xtext.xbase.Xbase.XExpression");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,22,FOLLOW_71); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_8, grammarAccess.getXBasicForLoopExpressionAccess().getSemicolonKeyword_6());
              		
            }
            // InternalDSLPolicies.g:4506:3: ( ( (lv_updateExpressions_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_updateExpressions_11_0= ruleXExpression ) ) )* )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==RULE_ID||(LA75_0>=RULE_INT && LA75_0<=RULE_DECIMAL)||LA75_0==23||LA75_0==41||(LA75_0>=57 && LA75_0<=58)||LA75_0==63||(LA75_0>=71 && LA75_0<=72)||LA75_0==74||LA75_0==77||LA75_0==79||(LA75_0>=83 && LA75_0<=85)||(LA75_0>=88 && LA75_0<=100)||LA75_0==102) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalDSLPolicies.g:4507:4: ( (lv_updateExpressions_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_updateExpressions_11_0= ruleXExpression ) ) )*
                    {
                    // InternalDSLPolicies.g:4507:4: ( (lv_updateExpressions_9_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:4508:5: (lv_updateExpressions_9_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:4508:5: (lv_updateExpressions_9_0= ruleXExpression )
                    // InternalDSLPolicies.g:4509:6: lv_updateExpressions_9_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getUpdateExpressionsXExpressionParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_46);
                    lv_updateExpressions_9_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
                      						}
                      						add(
                      							current,
                      							"updateExpressions",
                      							lv_updateExpressions_9_0,
                      							"org.eclipse.xtext.xbase.Xbase.XExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:4526:4: (otherlv_10= ',' ( (lv_updateExpressions_11_0= ruleXExpression ) ) )*
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==70) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:4527:5: otherlv_10= ',' ( (lv_updateExpressions_11_0= ruleXExpression ) )
                    	    {
                    	    otherlv_10=(Token)match(input,70,FOLLOW_21); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_10, grammarAccess.getXBasicForLoopExpressionAccess().getCommaKeyword_7_1_0());
                    	      				
                    	    }
                    	    // InternalDSLPolicies.g:4531:5: ( (lv_updateExpressions_11_0= ruleXExpression ) )
                    	    // InternalDSLPolicies.g:4532:6: (lv_updateExpressions_11_0= ruleXExpression )
                    	    {
                    	    // InternalDSLPolicies.g:4532:6: (lv_updateExpressions_11_0= ruleXExpression )
                    	    // InternalDSLPolicies.g:4533:7: lv_updateExpressions_11_0= ruleXExpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getUpdateExpressionsXExpressionParserRuleCall_7_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_46);
                    	    lv_updateExpressions_11_0=ruleXExpression();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"updateExpressions",
                    	      								lv_updateExpressions_11_0,
                    	      								"org.eclipse.xtext.xbase.Xbase.XExpression");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop74;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_12=(Token)match(input,24,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_12, grammarAccess.getXBasicForLoopExpressionAccess().getRightParenthesisKeyword_8());
              		
            }
            // InternalDSLPolicies.g:4556:3: ( (lv_eachExpression_13_0= ruleXExpression ) )
            // InternalDSLPolicies.g:4557:4: (lv_eachExpression_13_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:4557:4: (lv_eachExpression_13_0= ruleXExpression )
            // InternalDSLPolicies.g:4558:5: lv_eachExpression_13_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getEachExpressionXExpressionParserRuleCall_9_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_eachExpression_13_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
              					}
              					set(
              						current,
              						"eachExpression",
              						lv_eachExpression_13_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXBasicForLoopExpression"


    // $ANTLR start "entryRuleXWhileExpression"
    // InternalDSLPolicies.g:4579:1: entryRuleXWhileExpression returns [EObject current=null] : iv_ruleXWhileExpression= ruleXWhileExpression EOF ;
    public final EObject entryRuleXWhileExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXWhileExpression = null;


        try {
            // InternalDSLPolicies.g:4579:57: (iv_ruleXWhileExpression= ruleXWhileExpression EOF )
            // InternalDSLPolicies.g:4580:2: iv_ruleXWhileExpression= ruleXWhileExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXWhileExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXWhileExpression=ruleXWhileExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXWhileExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXWhileExpression"


    // $ANTLR start "ruleXWhileExpression"
    // InternalDSLPolicies.g:4586:1: ruleXWhileExpression returns [EObject current=null] : ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_predicate_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_body_5_0= ruleXExpression ) ) ) ;
    public final EObject ruleXWhileExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_predicate_3_0 = null;

        EObject lv_body_5_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:4592:2: ( ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_predicate_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_body_5_0= ruleXExpression ) ) ) )
            // InternalDSLPolicies.g:4593:2: ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_predicate_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_body_5_0= ruleXExpression ) ) )
            {
            // InternalDSLPolicies.g:4593:2: ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_predicate_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_body_5_0= ruleXExpression ) ) )
            // InternalDSLPolicies.g:4594:3: () otherlv_1= 'while' otherlv_2= '(' ( (lv_predicate_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_body_5_0= ruleXExpression ) )
            {
            // InternalDSLPolicies.g:4594:3: ()
            // InternalDSLPolicies.g:4595:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXWhileExpressionAccess().getXWhileExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,84,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXWhileExpressionAccess().getWhileKeyword_1());
              		
            }
            otherlv_2=(Token)match(input,23,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getXWhileExpressionAccess().getLeftParenthesisKeyword_2());
              		
            }
            // InternalDSLPolicies.g:4609:3: ( (lv_predicate_3_0= ruleXExpression ) )
            // InternalDSLPolicies.g:4610:4: (lv_predicate_3_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:4610:4: (lv_predicate_3_0= ruleXExpression )
            // InternalDSLPolicies.g:4611:5: lv_predicate_3_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXWhileExpressionAccess().getPredicateXExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_predicate_3_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXWhileExpressionRule());
              					}
              					set(
              						current,
              						"predicate",
              						lv_predicate_3_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_4=(Token)match(input,24,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getXWhileExpressionAccess().getRightParenthesisKeyword_4());
              		
            }
            // InternalDSLPolicies.g:4632:3: ( (lv_body_5_0= ruleXExpression ) )
            // InternalDSLPolicies.g:4633:4: (lv_body_5_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:4633:4: (lv_body_5_0= ruleXExpression )
            // InternalDSLPolicies.g:4634:5: lv_body_5_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXWhileExpressionAccess().getBodyXExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_5_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXWhileExpressionRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_5_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXWhileExpression"


    // $ANTLR start "entryRuleXDoWhileExpression"
    // InternalDSLPolicies.g:4655:1: entryRuleXDoWhileExpression returns [EObject current=null] : iv_ruleXDoWhileExpression= ruleXDoWhileExpression EOF ;
    public final EObject entryRuleXDoWhileExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXDoWhileExpression = null;


        try {
            // InternalDSLPolicies.g:4655:59: (iv_ruleXDoWhileExpression= ruleXDoWhileExpression EOF )
            // InternalDSLPolicies.g:4656:2: iv_ruleXDoWhileExpression= ruleXDoWhileExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXDoWhileExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXDoWhileExpression=ruleXDoWhileExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXDoWhileExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXDoWhileExpression"


    // $ANTLR start "ruleXDoWhileExpression"
    // InternalDSLPolicies.g:4662:1: ruleXDoWhileExpression returns [EObject current=null] : ( () otherlv_1= 'do' ( (lv_body_2_0= ruleXExpression ) ) otherlv_3= 'while' otherlv_4= '(' ( (lv_predicate_5_0= ruleXExpression ) ) otherlv_6= ')' ) ;
    public final EObject ruleXDoWhileExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_body_2_0 = null;

        EObject lv_predicate_5_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:4668:2: ( ( () otherlv_1= 'do' ( (lv_body_2_0= ruleXExpression ) ) otherlv_3= 'while' otherlv_4= '(' ( (lv_predicate_5_0= ruleXExpression ) ) otherlv_6= ')' ) )
            // InternalDSLPolicies.g:4669:2: ( () otherlv_1= 'do' ( (lv_body_2_0= ruleXExpression ) ) otherlv_3= 'while' otherlv_4= '(' ( (lv_predicate_5_0= ruleXExpression ) ) otherlv_6= ')' )
            {
            // InternalDSLPolicies.g:4669:2: ( () otherlv_1= 'do' ( (lv_body_2_0= ruleXExpression ) ) otherlv_3= 'while' otherlv_4= '(' ( (lv_predicate_5_0= ruleXExpression ) ) otherlv_6= ')' )
            // InternalDSLPolicies.g:4670:3: () otherlv_1= 'do' ( (lv_body_2_0= ruleXExpression ) ) otherlv_3= 'while' otherlv_4= '(' ( (lv_predicate_5_0= ruleXExpression ) ) otherlv_6= ')'
            {
            // InternalDSLPolicies.g:4670:3: ()
            // InternalDSLPolicies.g:4671:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXDoWhileExpressionAccess().getXDoWhileExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,85,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXDoWhileExpressionAccess().getDoKeyword_1());
              		
            }
            // InternalDSLPolicies.g:4681:3: ( (lv_body_2_0= ruleXExpression ) )
            // InternalDSLPolicies.g:4682:4: (lv_body_2_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:4682:4: (lv_body_2_0= ruleXExpression )
            // InternalDSLPolicies.g:4683:5: lv_body_2_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXDoWhileExpressionAccess().getBodyXExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_72);
            lv_body_2_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXDoWhileExpressionRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_2_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_3=(Token)match(input,84,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getXDoWhileExpressionAccess().getWhileKeyword_3());
              		
            }
            otherlv_4=(Token)match(input,23,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getXDoWhileExpressionAccess().getLeftParenthesisKeyword_4());
              		
            }
            // InternalDSLPolicies.g:4708:3: ( (lv_predicate_5_0= ruleXExpression ) )
            // InternalDSLPolicies.g:4709:4: (lv_predicate_5_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:4709:4: (lv_predicate_5_0= ruleXExpression )
            // InternalDSLPolicies.g:4710:5: lv_predicate_5_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXDoWhileExpressionAccess().getPredicateXExpressionParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_predicate_5_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXDoWhileExpressionRule());
              					}
              					set(
              						current,
              						"predicate",
              						lv_predicate_5_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_6=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getXDoWhileExpressionAccess().getRightParenthesisKeyword_6());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXDoWhileExpression"


    // $ANTLR start "entryRuleXBlockExpression"
    // InternalDSLPolicies.g:4735:1: entryRuleXBlockExpression returns [EObject current=null] : iv_ruleXBlockExpression= ruleXBlockExpression EOF ;
    public final EObject entryRuleXBlockExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXBlockExpression = null;


        try {
            // InternalDSLPolicies.g:4735:57: (iv_ruleXBlockExpression= ruleXBlockExpression EOF )
            // InternalDSLPolicies.g:4736:2: iv_ruleXBlockExpression= ruleXBlockExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXBlockExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXBlockExpression=ruleXBlockExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXBlockExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXBlockExpression"


    // $ANTLR start "ruleXBlockExpression"
    // InternalDSLPolicies.g:4742:1: ruleXBlockExpression returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_expressions_2_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_3= ';' )? )* otherlv_4= '}' ) ;
    public final EObject ruleXBlockExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_expressions_2_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:4748:2: ( ( () otherlv_1= '{' ( ( (lv_expressions_2_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_3= ';' )? )* otherlv_4= '}' ) )
            // InternalDSLPolicies.g:4749:2: ( () otherlv_1= '{' ( ( (lv_expressions_2_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_3= ';' )? )* otherlv_4= '}' )
            {
            // InternalDSLPolicies.g:4749:2: ( () otherlv_1= '{' ( ( (lv_expressions_2_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_3= ';' )? )* otherlv_4= '}' )
            // InternalDSLPolicies.g:4750:3: () otherlv_1= '{' ( ( (lv_expressions_2_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_3= ';' )? )* otherlv_4= '}'
            {
            // InternalDSLPolicies.g:4750:3: ()
            // InternalDSLPolicies.g:4751:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXBlockExpressionAccess().getXBlockExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,72,FOLLOW_73); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXBlockExpressionAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalDSLPolicies.g:4761:3: ( ( (lv_expressions_2_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_3= ';' )? )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==RULE_ID||(LA77_0>=RULE_INT && LA77_0<=RULE_DECIMAL)||LA77_0==23||LA77_0==41||(LA77_0>=57 && LA77_0<=58)||LA77_0==63||(LA77_0>=71 && LA77_0<=72)||LA77_0==74||LA77_0==77||LA77_0==79||(LA77_0>=83 && LA77_0<=100)||LA77_0==102) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // InternalDSLPolicies.g:4762:4: ( (lv_expressions_2_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_3= ';' )?
            	    {
            	    // InternalDSLPolicies.g:4762:4: ( (lv_expressions_2_0= ruleXExpressionOrVarDeclaration ) )
            	    // InternalDSLPolicies.g:4763:5: (lv_expressions_2_0= ruleXExpressionOrVarDeclaration )
            	    {
            	    // InternalDSLPolicies.g:4763:5: (lv_expressions_2_0= ruleXExpressionOrVarDeclaration )
            	    // InternalDSLPolicies.g:4764:6: lv_expressions_2_0= ruleXExpressionOrVarDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getXBlockExpressionAccess().getExpressionsXExpressionOrVarDeclarationParserRuleCall_2_0_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_74);
            	    lv_expressions_2_0=ruleXExpressionOrVarDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getXBlockExpressionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"expressions",
            	      							lv_expressions_2_0,
            	      							"org.eclipse.xtext.xbase.Xbase.XExpressionOrVarDeclaration");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }

            	    // InternalDSLPolicies.g:4781:4: (otherlv_3= ';' )?
            	    int alt76=2;
            	    int LA76_0 = input.LA(1);

            	    if ( (LA76_0==22) ) {
            	        alt76=1;
            	    }
            	    switch (alt76) {
            	        case 1 :
            	            // InternalDSLPolicies.g:4782:5: otherlv_3= ';'
            	            {
            	            otherlv_3=(Token)match(input,22,FOLLOW_73); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(otherlv_3, grammarAccess.getXBlockExpressionAccess().getSemicolonKeyword_2_1());
            	              				
            	            }

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop77;
                }
            } while (true);

            otherlv_4=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getXBlockExpressionAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXBlockExpression"


    // $ANTLR start "entryRuleXExpressionOrVarDeclaration"
    // InternalDSLPolicies.g:4796:1: entryRuleXExpressionOrVarDeclaration returns [EObject current=null] : iv_ruleXExpressionOrVarDeclaration= ruleXExpressionOrVarDeclaration EOF ;
    public final EObject entryRuleXExpressionOrVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXExpressionOrVarDeclaration = null;


        try {
            // InternalDSLPolicies.g:4796:68: (iv_ruleXExpressionOrVarDeclaration= ruleXExpressionOrVarDeclaration EOF )
            // InternalDSLPolicies.g:4797:2: iv_ruleXExpressionOrVarDeclaration= ruleXExpressionOrVarDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXExpressionOrVarDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXExpressionOrVarDeclaration=ruleXExpressionOrVarDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXExpressionOrVarDeclaration; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXExpressionOrVarDeclaration"


    // $ANTLR start "ruleXExpressionOrVarDeclaration"
    // InternalDSLPolicies.g:4803:1: ruleXExpressionOrVarDeclaration returns [EObject current=null] : (this_XVariableDeclaration_0= ruleXVariableDeclaration | this_XExpression_1= ruleXExpression ) ;
    public final EObject ruleXExpressionOrVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject this_XVariableDeclaration_0 = null;

        EObject this_XExpression_1 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:4809:2: ( (this_XVariableDeclaration_0= ruleXVariableDeclaration | this_XExpression_1= ruleXExpression ) )
            // InternalDSLPolicies.g:4810:2: (this_XVariableDeclaration_0= ruleXVariableDeclaration | this_XExpression_1= ruleXExpression )
            {
            // InternalDSLPolicies.g:4810:2: (this_XVariableDeclaration_0= ruleXVariableDeclaration | this_XExpression_1= ruleXExpression )
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( ((LA78_0>=86 && LA78_0<=87)) ) {
                alt78=1;
            }
            else if ( (LA78_0==RULE_ID||(LA78_0>=RULE_INT && LA78_0<=RULE_DECIMAL)||LA78_0==23||LA78_0==41||(LA78_0>=57 && LA78_0<=58)||LA78_0==63||(LA78_0>=71 && LA78_0<=72)||LA78_0==74||LA78_0==77||LA78_0==79||(LA78_0>=83 && LA78_0<=85)||(LA78_0>=88 && LA78_0<=100)||LA78_0==102) ) {
                alt78=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }
            switch (alt78) {
                case 1 :
                    // InternalDSLPolicies.g:4811:3: this_XVariableDeclaration_0= ruleXVariableDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXExpressionOrVarDeclarationAccess().getXVariableDeclarationParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XVariableDeclaration_0=ruleXVariableDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XVariableDeclaration_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:4820:3: this_XExpression_1= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getXExpressionOrVarDeclarationAccess().getXExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XExpression_1=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XExpression_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXExpressionOrVarDeclaration"


    // $ANTLR start "entryRuleXVariableDeclaration"
    // InternalDSLPolicies.g:4832:1: entryRuleXVariableDeclaration returns [EObject current=null] : iv_ruleXVariableDeclaration= ruleXVariableDeclaration EOF ;
    public final EObject entryRuleXVariableDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXVariableDeclaration = null;


        try {
            // InternalDSLPolicies.g:4832:61: (iv_ruleXVariableDeclaration= ruleXVariableDeclaration EOF )
            // InternalDSLPolicies.g:4833:2: iv_ruleXVariableDeclaration= ruleXVariableDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXVariableDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXVariableDeclaration=ruleXVariableDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXVariableDeclaration; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXVariableDeclaration"


    // $ANTLR start "ruleXVariableDeclaration"
    // InternalDSLPolicies.g:4839:1: ruleXVariableDeclaration returns [EObject current=null] : ( () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )? ) ;
    public final EObject ruleXVariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_writeable_1_0=null;
        Token otherlv_2=null;
        Token otherlv_6=null;
        EObject lv_type_3_0 = null;

        AntlrDatatypeRuleToken lv_name_4_0 = null;

        AntlrDatatypeRuleToken lv_name_5_0 = null;

        EObject lv_right_7_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:4845:2: ( ( () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )? ) )
            // InternalDSLPolicies.g:4846:2: ( () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )? )
            {
            // InternalDSLPolicies.g:4846:2: ( () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )? )
            // InternalDSLPolicies.g:4847:3: () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )?
            {
            // InternalDSLPolicies.g:4847:3: ()
            // InternalDSLPolicies.g:4848:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXVariableDeclarationAccess().getXVariableDeclarationAction_0(),
              					current);
              			
            }

            }

            // InternalDSLPolicies.g:4854:3: ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' )
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==86) ) {
                alt79=1;
            }
            else if ( (LA79_0==87) ) {
                alt79=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }
            switch (alt79) {
                case 1 :
                    // InternalDSLPolicies.g:4855:4: ( (lv_writeable_1_0= 'var' ) )
                    {
                    // InternalDSLPolicies.g:4855:4: ( (lv_writeable_1_0= 'var' ) )
                    // InternalDSLPolicies.g:4856:5: (lv_writeable_1_0= 'var' )
                    {
                    // InternalDSLPolicies.g:4856:5: (lv_writeable_1_0= 'var' )
                    // InternalDSLPolicies.g:4857:6: lv_writeable_1_0= 'var'
                    {
                    lv_writeable_1_0=(Token)match(input,86,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_writeable_1_0, grammarAccess.getXVariableDeclarationAccess().getWriteableVarKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getXVariableDeclarationRule());
                      						}
                      						setWithLastConsumed(current, "writeable", true, "var");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:4870:4: otherlv_2= 'val'
                    {
                    otherlv_2=(Token)match(input,87,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getXVariableDeclarationAccess().getValKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalDSLPolicies.g:4875:3: ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) )
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==RULE_ID) ) {
                int LA80_1 = input.LA(2);

                if ( (synpred29_InternalDSLPolicies()) ) {
                    alt80=1;
                }
                else if ( (true) ) {
                    alt80=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 80, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA80_0==23) && (synpred29_InternalDSLPolicies())) {
                alt80=1;
            }
            else if ( (LA80_0==54) && (synpred29_InternalDSLPolicies())) {
                alt80=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }
            switch (alt80) {
                case 1 :
                    // InternalDSLPolicies.g:4876:4: ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) )
                    {
                    // InternalDSLPolicies.g:4876:4: ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) )
                    // InternalDSLPolicies.g:4877:5: ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) )
                    {
                    // InternalDSLPolicies.g:4890:5: ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) )
                    // InternalDSLPolicies.g:4891:6: ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) )
                    {
                    // InternalDSLPolicies.g:4891:6: ( (lv_type_3_0= ruleJvmTypeReference ) )
                    // InternalDSLPolicies.g:4892:7: (lv_type_3_0= ruleJvmTypeReference )
                    {
                    // InternalDSLPolicies.g:4892:7: (lv_type_3_0= ruleJvmTypeReference )
                    // InternalDSLPolicies.g:4893:8: lv_type_3_0= ruleJvmTypeReference
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getTypeJvmTypeReferenceParserRuleCall_2_0_0_0_0());
                      							
                    }
                    pushFollow(FOLLOW_20);
                    lv_type_3_0=ruleJvmTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
                      								}
                      								set(
                      									current,
                      									"type",
                      									lv_type_3_0,
                      									"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }

                    // InternalDSLPolicies.g:4910:6: ( (lv_name_4_0= ruleValidID ) )
                    // InternalDSLPolicies.g:4911:7: (lv_name_4_0= ruleValidID )
                    {
                    // InternalDSLPolicies.g:4911:7: (lv_name_4_0= ruleValidID )
                    // InternalDSLPolicies.g:4912:8: lv_name_4_0= ruleValidID
                    {
                    if ( state.backtracking==0 ) {

                      								newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getNameValidIDParserRuleCall_2_0_0_1_0());
                      							
                    }
                    pushFollow(FOLLOW_75);
                    lv_name_4_0=ruleValidID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      								if (current==null) {
                      									current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
                      								}
                      								set(
                      									current,
                      									"name",
                      									lv_name_4_0,
                      									"org.eclipse.xtext.xbase.Xtype.ValidID");
                      								afterParserOrEnumRuleCall();
                      							
                    }

                    }


                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:4932:4: ( (lv_name_5_0= ruleValidID ) )
                    {
                    // InternalDSLPolicies.g:4932:4: ( (lv_name_5_0= ruleValidID ) )
                    // InternalDSLPolicies.g:4933:5: (lv_name_5_0= ruleValidID )
                    {
                    // InternalDSLPolicies.g:4933:5: (lv_name_5_0= ruleValidID )
                    // InternalDSLPolicies.g:4934:6: lv_name_5_0= ruleValidID
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getNameValidIDParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_75);
                    lv_name_5_0=ruleValidID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_5_0,
                      							"org.eclipse.xtext.xbase.Xtype.ValidID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalDSLPolicies.g:4952:3: (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==16) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // InternalDSLPolicies.g:4953:4: otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) )
                    {
                    otherlv_6=(Token)match(input,16,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getXVariableDeclarationAccess().getEqualsSignKeyword_3_0());
                      			
                    }
                    // InternalDSLPolicies.g:4957:4: ( (lv_right_7_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:4958:5: (lv_right_7_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:4958:5: (lv_right_7_0= ruleXExpression )
                    // InternalDSLPolicies.g:4959:6: lv_right_7_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getRightXExpressionParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_right_7_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_7_0,
                      							"org.eclipse.xtext.xbase.Xbase.XExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXVariableDeclaration"


    // $ANTLR start "entryRuleJvmFormalParameter"
    // InternalDSLPolicies.g:4981:1: entryRuleJvmFormalParameter returns [EObject current=null] : iv_ruleJvmFormalParameter= ruleJvmFormalParameter EOF ;
    public final EObject entryRuleJvmFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmFormalParameter = null;


        try {
            // InternalDSLPolicies.g:4981:59: (iv_ruleJvmFormalParameter= ruleJvmFormalParameter EOF )
            // InternalDSLPolicies.g:4982:2: iv_ruleJvmFormalParameter= ruleJvmFormalParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmFormalParameterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJvmFormalParameter=ruleJvmFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmFormalParameter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJvmFormalParameter"


    // $ANTLR start "ruleJvmFormalParameter"
    // InternalDSLPolicies.g:4988:1: ruleJvmFormalParameter returns [EObject current=null] : ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) ) ) ;
    public final EObject ruleJvmFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject lv_parameterType_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:4994:2: ( ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) ) ) )
            // InternalDSLPolicies.g:4995:2: ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) ) )
            {
            // InternalDSLPolicies.g:4995:2: ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) ) )
            // InternalDSLPolicies.g:4996:3: ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) )
            {
            // InternalDSLPolicies.g:4996:3: ( (lv_parameterType_0_0= ruleJvmTypeReference ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==RULE_ID) ) {
                int LA82_1 = input.LA(2);

                if ( (LA82_1==RULE_ID||LA82_1==41||LA82_1==67||LA82_1==74) ) {
                    alt82=1;
                }
            }
            else if ( (LA82_0==23||LA82_0==54) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalDSLPolicies.g:4997:4: (lv_parameterType_0_0= ruleJvmTypeReference )
                    {
                    // InternalDSLPolicies.g:4997:4: (lv_parameterType_0_0= ruleJvmTypeReference )
                    // InternalDSLPolicies.g:4998:5: lv_parameterType_0_0= ruleJvmTypeReference
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getJvmFormalParameterAccess().getParameterTypeJvmTypeReferenceParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_20);
                    lv_parameterType_0_0=ruleJvmTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getJvmFormalParameterRule());
                      					}
                      					set(
                      						current,
                      						"parameterType",
                      						lv_parameterType_0_0,
                      						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalDSLPolicies.g:5015:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalDSLPolicies.g:5016:4: (lv_name_1_0= ruleValidID )
            {
            // InternalDSLPolicies.g:5016:4: (lv_name_1_0= ruleValidID )
            // InternalDSLPolicies.g:5017:5: lv_name_1_0= ruleValidID
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getJvmFormalParameterAccess().getNameValidIDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_name_1_0=ruleValidID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getJvmFormalParameterRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.xtext.xbase.Xtype.ValidID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJvmFormalParameter"


    // $ANTLR start "entryRuleFullJvmFormalParameter"
    // InternalDSLPolicies.g:5038:1: entryRuleFullJvmFormalParameter returns [EObject current=null] : iv_ruleFullJvmFormalParameter= ruleFullJvmFormalParameter EOF ;
    public final EObject entryRuleFullJvmFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFullJvmFormalParameter = null;


        try {
            // InternalDSLPolicies.g:5038:63: (iv_ruleFullJvmFormalParameter= ruleFullJvmFormalParameter EOF )
            // InternalDSLPolicies.g:5039:2: iv_ruleFullJvmFormalParameter= ruleFullJvmFormalParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFullJvmFormalParameterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFullJvmFormalParameter=ruleFullJvmFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFullJvmFormalParameter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFullJvmFormalParameter"


    // $ANTLR start "ruleFullJvmFormalParameter"
    // InternalDSLPolicies.g:5045:1: ruleFullJvmFormalParameter returns [EObject current=null] : ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) ) ) ;
    public final EObject ruleFullJvmFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject lv_parameterType_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:5051:2: ( ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) ) ) )
            // InternalDSLPolicies.g:5052:2: ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) ) )
            {
            // InternalDSLPolicies.g:5052:2: ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) ) )
            // InternalDSLPolicies.g:5053:3: ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) )
            {
            // InternalDSLPolicies.g:5053:3: ( (lv_parameterType_0_0= ruleJvmTypeReference ) )
            // InternalDSLPolicies.g:5054:4: (lv_parameterType_0_0= ruleJvmTypeReference )
            {
            // InternalDSLPolicies.g:5054:4: (lv_parameterType_0_0= ruleJvmTypeReference )
            // InternalDSLPolicies.g:5055:5: lv_parameterType_0_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFullJvmFormalParameterAccess().getParameterTypeJvmTypeReferenceParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_20);
            lv_parameterType_0_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFullJvmFormalParameterRule());
              					}
              					set(
              						current,
              						"parameterType",
              						lv_parameterType_0_0,
              						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalDSLPolicies.g:5072:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalDSLPolicies.g:5073:4: (lv_name_1_0= ruleValidID )
            {
            // InternalDSLPolicies.g:5073:4: (lv_name_1_0= ruleValidID )
            // InternalDSLPolicies.g:5074:5: lv_name_1_0= ruleValidID
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFullJvmFormalParameterAccess().getNameValidIDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_name_1_0=ruleValidID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFullJvmFormalParameterRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.eclipse.xtext.xbase.Xtype.ValidID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFullJvmFormalParameter"


    // $ANTLR start "entryRuleXFeatureCall"
    // InternalDSLPolicies.g:5095:1: entryRuleXFeatureCall returns [EObject current=null] : iv_ruleXFeatureCall= ruleXFeatureCall EOF ;
    public final EObject entryRuleXFeatureCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXFeatureCall = null;


        try {
            // InternalDSLPolicies.g:5095:53: (iv_ruleXFeatureCall= ruleXFeatureCall EOF )
            // InternalDSLPolicies.g:5096:2: iv_ruleXFeatureCall= ruleXFeatureCall EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXFeatureCallRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXFeatureCall=ruleXFeatureCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXFeatureCall; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXFeatureCall"


    // $ANTLR start "ruleXFeatureCall"
    // InternalDSLPolicies.g:5102:1: ruleXFeatureCall returns [EObject current=null] : ( () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )? ) ;
    public final EObject ruleXFeatureCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_explicitOperationCall_7_0=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        EObject lv_typeArguments_2_0 = null;

        EObject lv_typeArguments_4_0 = null;

        EObject lv_featureCallArguments_8_0 = null;

        EObject lv_featureCallArguments_9_0 = null;

        EObject lv_featureCallArguments_11_0 = null;

        EObject lv_featureCallArguments_13_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:5108:2: ( ( () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )? ) )
            // InternalDSLPolicies.g:5109:2: ( () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )? )
            {
            // InternalDSLPolicies.g:5109:2: ( () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )? )
            // InternalDSLPolicies.g:5110:3: () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )?
            {
            // InternalDSLPolicies.g:5110:3: ()
            // InternalDSLPolicies.g:5111:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXFeatureCallAccess().getXFeatureCallAction_0(),
              					current);
              			
            }

            }

            // InternalDSLPolicies.g:5117:3: (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==41) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // InternalDSLPolicies.g:5118:4: otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>'
                    {
                    otherlv_1=(Token)match(input,41,FOLLOW_42); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getXFeatureCallAccess().getLessThanSignKeyword_1_0());
                      			
                    }
                    // InternalDSLPolicies.g:5122:4: ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) )
                    // InternalDSLPolicies.g:5123:5: (lv_typeArguments_2_0= ruleJvmArgumentTypeReference )
                    {
                    // InternalDSLPolicies.g:5123:5: (lv_typeArguments_2_0= ruleJvmArgumentTypeReference )
                    // InternalDSLPolicies.g:5124:6: lv_typeArguments_2_0= ruleJvmArgumentTypeReference
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_43);
                    lv_typeArguments_2_0=ruleJvmArgumentTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
                      						}
                      						add(
                      							current,
                      							"typeArguments",
                      							lv_typeArguments_2_0,
                      							"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:5141:4: (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )*
                    loop83:
                    do {
                        int alt83=2;
                        int LA83_0 = input.LA(1);

                        if ( (LA83_0==70) ) {
                            alt83=1;
                        }


                        switch (alt83) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:5142:5: otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) )
                    	    {
                    	    otherlv_3=(Token)match(input,70,FOLLOW_42); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getXFeatureCallAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalDSLPolicies.g:5146:5: ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) )
                    	    // InternalDSLPolicies.g:5147:6: (lv_typeArguments_4_0= ruleJvmArgumentTypeReference )
                    	    {
                    	    // InternalDSLPolicies.g:5147:6: (lv_typeArguments_4_0= ruleJvmArgumentTypeReference )
                    	    // InternalDSLPolicies.g:5148:7: lv_typeArguments_4_0= ruleJvmArgumentTypeReference
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getXFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_43);
                    	    lv_typeArguments_4_0=ruleJvmArgumentTypeReference();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"typeArguments",
                    	      								lv_typeArguments_4_0,
                    	      								"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop83;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,42,FOLLOW_41); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getXFeatureCallAccess().getGreaterThanSignKeyword_1_3());
                      			
                    }

                    }
                    break;

            }

            // InternalDSLPolicies.g:5171:3: ( ( ruleIdOrSuper ) )
            // InternalDSLPolicies.g:5172:4: ( ruleIdOrSuper )
            {
            // InternalDSLPolicies.g:5172:4: ( ruleIdOrSuper )
            // InternalDSLPolicies.g:5173:5: ruleIdOrSuper
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getXFeatureCallRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureJvmIdentifiableElementCrossReference_2_0());
              				
            }
            pushFollow(FOLLOW_76);
            ruleIdOrSuper();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalDSLPolicies.g:5187:3: ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )?
            int alt87=2;
            alt87 = dfa87.predict(input);
            switch (alt87) {
                case 1 :
                    // InternalDSLPolicies.g:5188:4: ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')'
                    {
                    // InternalDSLPolicies.g:5188:4: ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) )
                    // InternalDSLPolicies.g:5189:5: ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' )
                    {
                    // InternalDSLPolicies.g:5193:5: (lv_explicitOperationCall_7_0= '(' )
                    // InternalDSLPolicies.g:5194:6: lv_explicitOperationCall_7_0= '('
                    {
                    lv_explicitOperationCall_7_0=(Token)match(input,23,FOLLOW_45); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_explicitOperationCall_7_0, grammarAccess.getXFeatureCallAccess().getExplicitOperationCallLeftParenthesisKeyword_3_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getXFeatureCallRule());
                      						}
                      						setWithLastConsumed(current, "explicitOperationCall", true, "(");
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:5206:4: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )?
                    int alt86=3;
                    alt86 = dfa86.predict(input);
                    switch (alt86) {
                        case 1 :
                            // InternalDSLPolicies.g:5207:5: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) )
                            {
                            // InternalDSLPolicies.g:5207:5: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) )
                            // InternalDSLPolicies.g:5208:6: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure )
                            {
                            // InternalDSLPolicies.g:5233:6: (lv_featureCallArguments_8_0= ruleXShortClosure )
                            // InternalDSLPolicies.g:5234:7: lv_featureCallArguments_8_0= ruleXShortClosure
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXShortClosureParserRuleCall_3_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_17);
                            lv_featureCallArguments_8_0=ruleXShortClosure();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
                              							}
                              							add(
                              								current,
                              								"featureCallArguments",
                              								lv_featureCallArguments_8_0,
                              								"org.eclipse.xtext.xbase.Xbase.XShortClosure");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalDSLPolicies.g:5252:5: ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* )
                            {
                            // InternalDSLPolicies.g:5252:5: ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* )
                            // InternalDSLPolicies.g:5253:6: ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )*
                            {
                            // InternalDSLPolicies.g:5253:6: ( (lv_featureCallArguments_9_0= ruleXExpression ) )
                            // InternalDSLPolicies.g:5254:7: (lv_featureCallArguments_9_0= ruleXExpression )
                            {
                            // InternalDSLPolicies.g:5254:7: (lv_featureCallArguments_9_0= ruleXExpression )
                            // InternalDSLPolicies.g:5255:8: lv_featureCallArguments_9_0= ruleXExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXExpressionParserRuleCall_3_1_1_0_0());
                              							
                            }
                            pushFollow(FOLLOW_46);
                            lv_featureCallArguments_9_0=ruleXExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
                              								}
                              								add(
                              									current,
                              									"featureCallArguments",
                              									lv_featureCallArguments_9_0,
                              									"org.eclipse.xtext.xbase.Xbase.XExpression");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            // InternalDSLPolicies.g:5272:6: (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )*
                            loop85:
                            do {
                                int alt85=2;
                                int LA85_0 = input.LA(1);

                                if ( (LA85_0==70) ) {
                                    alt85=1;
                                }


                                switch (alt85) {
                            	case 1 :
                            	    // InternalDSLPolicies.g:5273:7: otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) )
                            	    {
                            	    otherlv_10=(Token)match(input,70,FOLLOW_21); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_10, grammarAccess.getXFeatureCallAccess().getCommaKeyword_3_1_1_1_0());
                            	      						
                            	    }
                            	    // InternalDSLPolicies.g:5277:7: ( (lv_featureCallArguments_11_0= ruleXExpression ) )
                            	    // InternalDSLPolicies.g:5278:8: (lv_featureCallArguments_11_0= ruleXExpression )
                            	    {
                            	    // InternalDSLPolicies.g:5278:8: (lv_featureCallArguments_11_0= ruleXExpression )
                            	    // InternalDSLPolicies.g:5279:9: lv_featureCallArguments_11_0= ruleXExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXExpressionParserRuleCall_3_1_1_1_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_46);
                            	    lv_featureCallArguments_11_0=ruleXExpression();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
                            	      									}
                            	      									add(
                            	      										current,
                            	      										"featureCallArguments",
                            	      										lv_featureCallArguments_11_0,
                            	      										"org.eclipse.xtext.xbase.Xbase.XExpression");
                            	      									afterParserOrEnumRuleCall();
                            	      								
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop85;
                                }
                            } while (true);


                            }


                            }
                            break;

                    }

                    otherlv_12=(Token)match(input,24,FOLLOW_77); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getXFeatureCallAccess().getRightParenthesisKeyword_3_2());
                      			
                    }

                    }
                    break;

            }

            // InternalDSLPolicies.g:5304:3: ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )?
            int alt88=2;
            alt88 = dfa88.predict(input);
            switch (alt88) {
                case 1 :
                    // InternalDSLPolicies.g:5305:4: ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure )
                    {
                    // InternalDSLPolicies.g:5311:4: (lv_featureCallArguments_13_0= ruleXClosure )
                    // InternalDSLPolicies.g:5312:5: lv_featureCallArguments_13_0= ruleXClosure
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXClosureParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_featureCallArguments_13_0=ruleXClosure();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
                      					}
                      					add(
                      						current,
                      						"featureCallArguments",
                      						lv_featureCallArguments_13_0,
                      						"org.eclipse.xtext.xbase.Xbase.XClosure");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXFeatureCall"


    // $ANTLR start "entryRuleFeatureCallID"
    // InternalDSLPolicies.g:5333:1: entryRuleFeatureCallID returns [String current=null] : iv_ruleFeatureCallID= ruleFeatureCallID EOF ;
    public final String entryRuleFeatureCallID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFeatureCallID = null;


        try {
            // InternalDSLPolicies.g:5333:53: (iv_ruleFeatureCallID= ruleFeatureCallID EOF )
            // InternalDSLPolicies.g:5334:2: iv_ruleFeatureCallID= ruleFeatureCallID EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFeatureCallIDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFeatureCallID=ruleFeatureCallID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFeatureCallID.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFeatureCallID"


    // $ANTLR start "ruleFeatureCallID"
    // InternalDSLPolicies.g:5340:1: ruleFeatureCallID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ValidID_0= ruleValidID | kw= 'extends' | kw= 'static' | kw= 'import' | kw= 'extension' ) ;
    public final AntlrDatatypeRuleToken ruleFeatureCallID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ValidID_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:5346:2: ( (this_ValidID_0= ruleValidID | kw= 'extends' | kw= 'static' | kw= 'import' | kw= 'extension' ) )
            // InternalDSLPolicies.g:5347:2: (this_ValidID_0= ruleValidID | kw= 'extends' | kw= 'static' | kw= 'import' | kw= 'extension' )
            {
            // InternalDSLPolicies.g:5347:2: (this_ValidID_0= ruleValidID | kw= 'extends' | kw= 'static' | kw= 'import' | kw= 'extension' )
            int alt89=5;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt89=1;
                }
                break;
            case 88:
                {
                alt89=2;
                }
                break;
            case 89:
                {
                alt89=3;
                }
                break;
            case 90:
                {
                alt89=4;
                }
                break;
            case 91:
                {
                alt89=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }

            switch (alt89) {
                case 1 :
                    // InternalDSLPolicies.g:5348:3: this_ValidID_0= ruleValidID
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getFeatureCallIDAccess().getValidIDParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ValidID_0=ruleValidID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ValidID_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:5359:3: kw= 'extends'
                    {
                    kw=(Token)match(input,88,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getExtendsKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:5365:3: kw= 'static'
                    {
                    kw=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getStaticKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalDSLPolicies.g:5371:3: kw= 'import'
                    {
                    kw=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getImportKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalDSLPolicies.g:5377:3: kw= 'extension'
                    {
                    kw=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getExtensionKeyword_4());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFeatureCallID"


    // $ANTLR start "entryRuleIdOrSuper"
    // InternalDSLPolicies.g:5386:1: entryRuleIdOrSuper returns [String current=null] : iv_ruleIdOrSuper= ruleIdOrSuper EOF ;
    public final String entryRuleIdOrSuper() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleIdOrSuper = null;


        try {
            // InternalDSLPolicies.g:5386:49: (iv_ruleIdOrSuper= ruleIdOrSuper EOF )
            // InternalDSLPolicies.g:5387:2: iv_ruleIdOrSuper= ruleIdOrSuper EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIdOrSuperRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIdOrSuper=ruleIdOrSuper();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIdOrSuper.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIdOrSuper"


    // $ANTLR start "ruleIdOrSuper"
    // InternalDSLPolicies.g:5393:1: ruleIdOrSuper returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_FeatureCallID_0= ruleFeatureCallID | kw= 'super' ) ;
    public final AntlrDatatypeRuleToken ruleIdOrSuper() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_FeatureCallID_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:5399:2: ( (this_FeatureCallID_0= ruleFeatureCallID | kw= 'super' ) )
            // InternalDSLPolicies.g:5400:2: (this_FeatureCallID_0= ruleFeatureCallID | kw= 'super' )
            {
            // InternalDSLPolicies.g:5400:2: (this_FeatureCallID_0= ruleFeatureCallID | kw= 'super' )
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==RULE_ID||(LA90_0>=88 && LA90_0<=91)) ) {
                alt90=1;
            }
            else if ( (LA90_0==92) ) {
                alt90=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }
            switch (alt90) {
                case 1 :
                    // InternalDSLPolicies.g:5401:3: this_FeatureCallID_0= ruleFeatureCallID
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getIdOrSuperAccess().getFeatureCallIDParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_FeatureCallID_0=ruleFeatureCallID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_FeatureCallID_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:5412:3: kw= 'super'
                    {
                    kw=(Token)match(input,92,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getIdOrSuperAccess().getSuperKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIdOrSuper"


    // $ANTLR start "entryRuleXConstructorCall"
    // InternalDSLPolicies.g:5421:1: entryRuleXConstructorCall returns [EObject current=null] : iv_ruleXConstructorCall= ruleXConstructorCall EOF ;
    public final EObject entryRuleXConstructorCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXConstructorCall = null;


        try {
            // InternalDSLPolicies.g:5421:57: (iv_ruleXConstructorCall= ruleXConstructorCall EOF )
            // InternalDSLPolicies.g:5422:2: iv_ruleXConstructorCall= ruleXConstructorCall EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXConstructorCallRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXConstructorCall=ruleXConstructorCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXConstructorCall; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXConstructorCall"


    // $ANTLR start "ruleXConstructorCall"
    // InternalDSLPolicies.g:5428:1: ruleXConstructorCall returns [EObject current=null] : ( () otherlv_1= 'new' ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_3= '<' ) ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) (otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) ) )* otherlv_7= '>' )? ( ( ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )? otherlv_13= ')' )? ( ( ( () '[' ) )=> (lv_arguments_14_0= ruleXClosure ) )? ) ;
    public final EObject ruleXConstructorCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token lv_explicitConstructorCall_8_0=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        EObject lv_typeArguments_4_0 = null;

        EObject lv_typeArguments_6_0 = null;

        EObject lv_arguments_9_0 = null;

        EObject lv_arguments_10_0 = null;

        EObject lv_arguments_12_0 = null;

        EObject lv_arguments_14_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:5434:2: ( ( () otherlv_1= 'new' ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_3= '<' ) ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) (otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) ) )* otherlv_7= '>' )? ( ( ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )? otherlv_13= ')' )? ( ( ( () '[' ) )=> (lv_arguments_14_0= ruleXClosure ) )? ) )
            // InternalDSLPolicies.g:5435:2: ( () otherlv_1= 'new' ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_3= '<' ) ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) (otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) ) )* otherlv_7= '>' )? ( ( ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )? otherlv_13= ')' )? ( ( ( () '[' ) )=> (lv_arguments_14_0= ruleXClosure ) )? )
            {
            // InternalDSLPolicies.g:5435:2: ( () otherlv_1= 'new' ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_3= '<' ) ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) (otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) ) )* otherlv_7= '>' )? ( ( ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )? otherlv_13= ')' )? ( ( ( () '[' ) )=> (lv_arguments_14_0= ruleXClosure ) )? )
            // InternalDSLPolicies.g:5436:3: () otherlv_1= 'new' ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_3= '<' ) ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) (otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) ) )* otherlv_7= '>' )? ( ( ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )? otherlv_13= ')' )? ( ( ( () '[' ) )=> (lv_arguments_14_0= ruleXClosure ) )?
            {
            // InternalDSLPolicies.g:5436:3: ()
            // InternalDSLPolicies.g:5437:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXConstructorCallAccess().getXConstructorCallAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,93,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXConstructorCallAccess().getNewKeyword_1());
              		
            }
            // InternalDSLPolicies.g:5447:3: ( ( ruleQualifiedName ) )
            // InternalDSLPolicies.g:5448:4: ( ruleQualifiedName )
            {
            // InternalDSLPolicies.g:5448:4: ( ruleQualifiedName )
            // InternalDSLPolicies.g:5449:5: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getXConstructorCallRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXConstructorCallAccess().getConstructorJvmConstructorCrossReference_2_0());
              				
            }
            pushFollow(FOLLOW_78);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalDSLPolicies.g:5463:3: ( ( ( '<' )=>otherlv_3= '<' ) ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) (otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) ) )* otherlv_7= '>' )?
            int alt92=2;
            alt92 = dfa92.predict(input);
            switch (alt92) {
                case 1 :
                    // InternalDSLPolicies.g:5464:4: ( ( '<' )=>otherlv_3= '<' ) ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) (otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) ) )* otherlv_7= '>'
                    {
                    // InternalDSLPolicies.g:5464:4: ( ( '<' )=>otherlv_3= '<' )
                    // InternalDSLPolicies.g:5465:5: ( '<' )=>otherlv_3= '<'
                    {
                    otherlv_3=(Token)match(input,41,FOLLOW_42); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getXConstructorCallAccess().getLessThanSignKeyword_3_0());
                      				
                    }

                    }

                    // InternalDSLPolicies.g:5471:4: ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) )
                    // InternalDSLPolicies.g:5472:5: (lv_typeArguments_4_0= ruleJvmArgumentTypeReference )
                    {
                    // InternalDSLPolicies.g:5472:5: (lv_typeArguments_4_0= ruleJvmArgumentTypeReference )
                    // InternalDSLPolicies.g:5473:6: lv_typeArguments_4_0= ruleJvmArgumentTypeReference
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXConstructorCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_43);
                    lv_typeArguments_4_0=ruleJvmArgumentTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
                      						}
                      						add(
                      							current,
                      							"typeArguments",
                      							lv_typeArguments_4_0,
                      							"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:5490:4: (otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) ) )*
                    loop91:
                    do {
                        int alt91=2;
                        int LA91_0 = input.LA(1);

                        if ( (LA91_0==70) ) {
                            alt91=1;
                        }


                        switch (alt91) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:5491:5: otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) )
                    	    {
                    	    otherlv_5=(Token)match(input,70,FOLLOW_42); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_5, grammarAccess.getXConstructorCallAccess().getCommaKeyword_3_2_0());
                    	      				
                    	    }
                    	    // InternalDSLPolicies.g:5495:5: ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) )
                    	    // InternalDSLPolicies.g:5496:6: (lv_typeArguments_6_0= ruleJvmArgumentTypeReference )
                    	    {
                    	    // InternalDSLPolicies.g:5496:6: (lv_typeArguments_6_0= ruleJvmArgumentTypeReference )
                    	    // InternalDSLPolicies.g:5497:7: lv_typeArguments_6_0= ruleJvmArgumentTypeReference
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getXConstructorCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_3_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_43);
                    	    lv_typeArguments_6_0=ruleJvmArgumentTypeReference();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"typeArguments",
                    	      								lv_typeArguments_6_0,
                    	      								"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop91;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,42,FOLLOW_76); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getXConstructorCallAccess().getGreaterThanSignKeyword_3_3());
                      			
                    }

                    }
                    break;

            }

            // InternalDSLPolicies.g:5520:3: ( ( ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )? otherlv_13= ')' )?
            int alt95=2;
            alt95 = dfa95.predict(input);
            switch (alt95) {
                case 1 :
                    // InternalDSLPolicies.g:5521:4: ( ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )? otherlv_13= ')'
                    {
                    // InternalDSLPolicies.g:5521:4: ( ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' ) )
                    // InternalDSLPolicies.g:5522:5: ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' )
                    {
                    // InternalDSLPolicies.g:5526:5: (lv_explicitConstructorCall_8_0= '(' )
                    // InternalDSLPolicies.g:5527:6: lv_explicitConstructorCall_8_0= '('
                    {
                    lv_explicitConstructorCall_8_0=(Token)match(input,23,FOLLOW_45); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_explicitConstructorCall_8_0, grammarAccess.getXConstructorCallAccess().getExplicitConstructorCallLeftParenthesisKeyword_4_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getXConstructorCallRule());
                      						}
                      						setWithLastConsumed(current, "explicitConstructorCall", true, "(");
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:5539:4: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )?
                    int alt94=3;
                    alt94 = dfa94.predict(input);
                    switch (alt94) {
                        case 1 :
                            // InternalDSLPolicies.g:5540:5: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) )
                            {
                            // InternalDSLPolicies.g:5540:5: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) )
                            // InternalDSLPolicies.g:5541:6: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure )
                            {
                            // InternalDSLPolicies.g:5566:6: (lv_arguments_9_0= ruleXShortClosure )
                            // InternalDSLPolicies.g:5567:7: lv_arguments_9_0= ruleXShortClosure
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getXConstructorCallAccess().getArgumentsXShortClosureParserRuleCall_4_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_17);
                            lv_arguments_9_0=ruleXShortClosure();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
                              							}
                              							add(
                              								current,
                              								"arguments",
                              								lv_arguments_9_0,
                              								"org.eclipse.xtext.xbase.Xbase.XShortClosure");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalDSLPolicies.g:5585:5: ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* )
                            {
                            // InternalDSLPolicies.g:5585:5: ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* )
                            // InternalDSLPolicies.g:5586:6: ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )*
                            {
                            // InternalDSLPolicies.g:5586:6: ( (lv_arguments_10_0= ruleXExpression ) )
                            // InternalDSLPolicies.g:5587:7: (lv_arguments_10_0= ruleXExpression )
                            {
                            // InternalDSLPolicies.g:5587:7: (lv_arguments_10_0= ruleXExpression )
                            // InternalDSLPolicies.g:5588:8: lv_arguments_10_0= ruleXExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getXConstructorCallAccess().getArgumentsXExpressionParserRuleCall_4_1_1_0_0());
                              							
                            }
                            pushFollow(FOLLOW_46);
                            lv_arguments_10_0=ruleXExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
                              								}
                              								add(
                              									current,
                              									"arguments",
                              									lv_arguments_10_0,
                              									"org.eclipse.xtext.xbase.Xbase.XExpression");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            // InternalDSLPolicies.g:5605:6: (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )*
                            loop93:
                            do {
                                int alt93=2;
                                int LA93_0 = input.LA(1);

                                if ( (LA93_0==70) ) {
                                    alt93=1;
                                }


                                switch (alt93) {
                            	case 1 :
                            	    // InternalDSLPolicies.g:5606:7: otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) )
                            	    {
                            	    otherlv_11=(Token)match(input,70,FOLLOW_21); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							newLeafNode(otherlv_11, grammarAccess.getXConstructorCallAccess().getCommaKeyword_4_1_1_1_0());
                            	      						
                            	    }
                            	    // InternalDSLPolicies.g:5610:7: ( (lv_arguments_12_0= ruleXExpression ) )
                            	    // InternalDSLPolicies.g:5611:8: (lv_arguments_12_0= ruleXExpression )
                            	    {
                            	    // InternalDSLPolicies.g:5611:8: (lv_arguments_12_0= ruleXExpression )
                            	    // InternalDSLPolicies.g:5612:9: lv_arguments_12_0= ruleXExpression
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      									newCompositeNode(grammarAccess.getXConstructorCallAccess().getArgumentsXExpressionParserRuleCall_4_1_1_1_1_0());
                            	      								
                            	    }
                            	    pushFollow(FOLLOW_46);
                            	    lv_arguments_12_0=ruleXExpression();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      									if (current==null) {
                            	      										current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
                            	      									}
                            	      									add(
                            	      										current,
                            	      										"arguments",
                            	      										lv_arguments_12_0,
                            	      										"org.eclipse.xtext.xbase.Xbase.XExpression");
                            	      									afterParserOrEnumRuleCall();
                            	      								
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop93;
                                }
                            } while (true);


                            }


                            }
                            break;

                    }

                    otherlv_13=(Token)match(input,24,FOLLOW_77); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getXConstructorCallAccess().getRightParenthesisKeyword_4_2());
                      			
                    }

                    }
                    break;

            }

            // InternalDSLPolicies.g:5637:3: ( ( ( () '[' ) )=> (lv_arguments_14_0= ruleXClosure ) )?
            int alt96=2;
            alt96 = dfa96.predict(input);
            switch (alt96) {
                case 1 :
                    // InternalDSLPolicies.g:5638:4: ( ( () '[' ) )=> (lv_arguments_14_0= ruleXClosure )
                    {
                    // InternalDSLPolicies.g:5644:4: (lv_arguments_14_0= ruleXClosure )
                    // InternalDSLPolicies.g:5645:5: lv_arguments_14_0= ruleXClosure
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getXConstructorCallAccess().getArgumentsXClosureParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_arguments_14_0=ruleXClosure();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
                      					}
                      					add(
                      						current,
                      						"arguments",
                      						lv_arguments_14_0,
                      						"org.eclipse.xtext.xbase.Xbase.XClosure");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXConstructorCall"


    // $ANTLR start "entryRuleXBooleanLiteral"
    // InternalDSLPolicies.g:5666:1: entryRuleXBooleanLiteral returns [EObject current=null] : iv_ruleXBooleanLiteral= ruleXBooleanLiteral EOF ;
    public final EObject entryRuleXBooleanLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXBooleanLiteral = null;


        try {
            // InternalDSLPolicies.g:5666:56: (iv_ruleXBooleanLiteral= ruleXBooleanLiteral EOF )
            // InternalDSLPolicies.g:5667:2: iv_ruleXBooleanLiteral= ruleXBooleanLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXBooleanLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXBooleanLiteral=ruleXBooleanLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXBooleanLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXBooleanLiteral"


    // $ANTLR start "ruleXBooleanLiteral"
    // InternalDSLPolicies.g:5673:1: ruleXBooleanLiteral returns [EObject current=null] : ( () (otherlv_1= 'false' | ( (lv_isTrue_2_0= 'true' ) ) ) ) ;
    public final EObject ruleXBooleanLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_isTrue_2_0=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:5679:2: ( ( () (otherlv_1= 'false' | ( (lv_isTrue_2_0= 'true' ) ) ) ) )
            // InternalDSLPolicies.g:5680:2: ( () (otherlv_1= 'false' | ( (lv_isTrue_2_0= 'true' ) ) ) )
            {
            // InternalDSLPolicies.g:5680:2: ( () (otherlv_1= 'false' | ( (lv_isTrue_2_0= 'true' ) ) ) )
            // InternalDSLPolicies.g:5681:3: () (otherlv_1= 'false' | ( (lv_isTrue_2_0= 'true' ) ) )
            {
            // InternalDSLPolicies.g:5681:3: ()
            // InternalDSLPolicies.g:5682:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXBooleanLiteralAccess().getXBooleanLiteralAction_0(),
              					current);
              			
            }

            }

            // InternalDSLPolicies.g:5688:3: (otherlv_1= 'false' | ( (lv_isTrue_2_0= 'true' ) ) )
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==94) ) {
                alt97=1;
            }
            else if ( (LA97_0==95) ) {
                alt97=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }
            switch (alt97) {
                case 1 :
                    // InternalDSLPolicies.g:5689:4: otherlv_1= 'false'
                    {
                    otherlv_1=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getXBooleanLiteralAccess().getFalseKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:5694:4: ( (lv_isTrue_2_0= 'true' ) )
                    {
                    // InternalDSLPolicies.g:5694:4: ( (lv_isTrue_2_0= 'true' ) )
                    // InternalDSLPolicies.g:5695:5: (lv_isTrue_2_0= 'true' )
                    {
                    // InternalDSLPolicies.g:5695:5: (lv_isTrue_2_0= 'true' )
                    // InternalDSLPolicies.g:5696:6: lv_isTrue_2_0= 'true'
                    {
                    lv_isTrue_2_0=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_isTrue_2_0, grammarAccess.getXBooleanLiteralAccess().getIsTrueTrueKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getXBooleanLiteralRule());
                      						}
                      						setWithLastConsumed(current, "isTrue", true, "true");
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXBooleanLiteral"


    // $ANTLR start "entryRuleXNullLiteral"
    // InternalDSLPolicies.g:5713:1: entryRuleXNullLiteral returns [EObject current=null] : iv_ruleXNullLiteral= ruleXNullLiteral EOF ;
    public final EObject entryRuleXNullLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXNullLiteral = null;


        try {
            // InternalDSLPolicies.g:5713:53: (iv_ruleXNullLiteral= ruleXNullLiteral EOF )
            // InternalDSLPolicies.g:5714:2: iv_ruleXNullLiteral= ruleXNullLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXNullLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXNullLiteral=ruleXNullLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXNullLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXNullLiteral"


    // $ANTLR start "ruleXNullLiteral"
    // InternalDSLPolicies.g:5720:1: ruleXNullLiteral returns [EObject current=null] : ( () otherlv_1= 'null' ) ;
    public final EObject ruleXNullLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:5726:2: ( ( () otherlv_1= 'null' ) )
            // InternalDSLPolicies.g:5727:2: ( () otherlv_1= 'null' )
            {
            // InternalDSLPolicies.g:5727:2: ( () otherlv_1= 'null' )
            // InternalDSLPolicies.g:5728:3: () otherlv_1= 'null'
            {
            // InternalDSLPolicies.g:5728:3: ()
            // InternalDSLPolicies.g:5729:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXNullLiteralAccess().getXNullLiteralAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,96,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXNullLiteralAccess().getNullKeyword_1());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXNullLiteral"


    // $ANTLR start "entryRuleXNumberLiteral"
    // InternalDSLPolicies.g:5743:1: entryRuleXNumberLiteral returns [EObject current=null] : iv_ruleXNumberLiteral= ruleXNumberLiteral EOF ;
    public final EObject entryRuleXNumberLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXNumberLiteral = null;


        try {
            // InternalDSLPolicies.g:5743:55: (iv_ruleXNumberLiteral= ruleXNumberLiteral EOF )
            // InternalDSLPolicies.g:5744:2: iv_ruleXNumberLiteral= ruleXNumberLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXNumberLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXNumberLiteral=ruleXNumberLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXNumberLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXNumberLiteral"


    // $ANTLR start "ruleXNumberLiteral"
    // InternalDSLPolicies.g:5750:1: ruleXNumberLiteral returns [EObject current=null] : ( () ( (lv_value_1_0= ruleNumber ) ) ) ;
    public final EObject ruleXNumberLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:5756:2: ( ( () ( (lv_value_1_0= ruleNumber ) ) ) )
            // InternalDSLPolicies.g:5757:2: ( () ( (lv_value_1_0= ruleNumber ) ) )
            {
            // InternalDSLPolicies.g:5757:2: ( () ( (lv_value_1_0= ruleNumber ) ) )
            // InternalDSLPolicies.g:5758:3: () ( (lv_value_1_0= ruleNumber ) )
            {
            // InternalDSLPolicies.g:5758:3: ()
            // InternalDSLPolicies.g:5759:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXNumberLiteralAccess().getXNumberLiteralAction_0(),
              					current);
              			
            }

            }

            // InternalDSLPolicies.g:5765:3: ( (lv_value_1_0= ruleNumber ) )
            // InternalDSLPolicies.g:5766:4: (lv_value_1_0= ruleNumber )
            {
            // InternalDSLPolicies.g:5766:4: (lv_value_1_0= ruleNumber )
            // InternalDSLPolicies.g:5767:5: lv_value_1_0= ruleNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXNumberLiteralAccess().getValueNumberParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_1_0=ruleNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXNumberLiteralRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_1_0,
              						"org.eclipse.xtext.xbase.Xbase.Number");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXNumberLiteral"


    // $ANTLR start "entryRuleXStringLiteral"
    // InternalDSLPolicies.g:5788:1: entryRuleXStringLiteral returns [EObject current=null] : iv_ruleXStringLiteral= ruleXStringLiteral EOF ;
    public final EObject entryRuleXStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXStringLiteral = null;


        try {
            // InternalDSLPolicies.g:5788:55: (iv_ruleXStringLiteral= ruleXStringLiteral EOF )
            // InternalDSLPolicies.g:5789:2: iv_ruleXStringLiteral= ruleXStringLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXStringLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXStringLiteral=ruleXStringLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXStringLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXStringLiteral"


    // $ANTLR start "ruleXStringLiteral"
    // InternalDSLPolicies.g:5795:1: ruleXStringLiteral returns [EObject current=null] : ( () ( (lv_value_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleXStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_1_0=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:5801:2: ( ( () ( (lv_value_1_0= RULE_STRING ) ) ) )
            // InternalDSLPolicies.g:5802:2: ( () ( (lv_value_1_0= RULE_STRING ) ) )
            {
            // InternalDSLPolicies.g:5802:2: ( () ( (lv_value_1_0= RULE_STRING ) ) )
            // InternalDSLPolicies.g:5803:3: () ( (lv_value_1_0= RULE_STRING ) )
            {
            // InternalDSLPolicies.g:5803:3: ()
            // InternalDSLPolicies.g:5804:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXStringLiteralAccess().getXStringLiteralAction_0(),
              					current);
              			
            }

            }

            // InternalDSLPolicies.g:5810:3: ( (lv_value_1_0= RULE_STRING ) )
            // InternalDSLPolicies.g:5811:4: (lv_value_1_0= RULE_STRING )
            {
            // InternalDSLPolicies.g:5811:4: (lv_value_1_0= RULE_STRING )
            // InternalDSLPolicies.g:5812:5: lv_value_1_0= RULE_STRING
            {
            lv_value_1_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_value_1_0, grammarAccess.getXStringLiteralAccess().getValueSTRINGTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getXStringLiteralRule());
              					}
              					setWithLastConsumed(
              						current,
              						"value",
              						lv_value_1_0,
              						"org.eclipse.xtext.xbase.Xtype.STRING");
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXStringLiteral"


    // $ANTLR start "entryRuleXTypeLiteral"
    // InternalDSLPolicies.g:5832:1: entryRuleXTypeLiteral returns [EObject current=null] : iv_ruleXTypeLiteral= ruleXTypeLiteral EOF ;
    public final EObject entryRuleXTypeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXTypeLiteral = null;


        try {
            // InternalDSLPolicies.g:5832:53: (iv_ruleXTypeLiteral= ruleXTypeLiteral EOF )
            // InternalDSLPolicies.g:5833:2: iv_ruleXTypeLiteral= ruleXTypeLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXTypeLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXTypeLiteral=ruleXTypeLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXTypeLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXTypeLiteral"


    // $ANTLR start "ruleXTypeLiteral"
    // InternalDSLPolicies.g:5839:1: ruleXTypeLiteral returns [EObject current=null] : ( () otherlv_1= 'typeof' otherlv_2= '(' ( ( ruleQualifiedName ) ) ( (lv_arrayDimensions_4_0= ruleArrayBrackets ) )* otherlv_5= ')' ) ;
    public final EObject ruleXTypeLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_arrayDimensions_4_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:5845:2: ( ( () otherlv_1= 'typeof' otherlv_2= '(' ( ( ruleQualifiedName ) ) ( (lv_arrayDimensions_4_0= ruleArrayBrackets ) )* otherlv_5= ')' ) )
            // InternalDSLPolicies.g:5846:2: ( () otherlv_1= 'typeof' otherlv_2= '(' ( ( ruleQualifiedName ) ) ( (lv_arrayDimensions_4_0= ruleArrayBrackets ) )* otherlv_5= ')' )
            {
            // InternalDSLPolicies.g:5846:2: ( () otherlv_1= 'typeof' otherlv_2= '(' ( ( ruleQualifiedName ) ) ( (lv_arrayDimensions_4_0= ruleArrayBrackets ) )* otherlv_5= ')' )
            // InternalDSLPolicies.g:5847:3: () otherlv_1= 'typeof' otherlv_2= '(' ( ( ruleQualifiedName ) ) ( (lv_arrayDimensions_4_0= ruleArrayBrackets ) )* otherlv_5= ')'
            {
            // InternalDSLPolicies.g:5847:3: ()
            // InternalDSLPolicies.g:5848:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXTypeLiteralAccess().getXTypeLiteralAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,97,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXTypeLiteralAccess().getTypeofKeyword_1());
              		
            }
            otherlv_2=(Token)match(input,23,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getXTypeLiteralAccess().getLeftParenthesisKeyword_2());
              		
            }
            // InternalDSLPolicies.g:5862:3: ( ( ruleQualifiedName ) )
            // InternalDSLPolicies.g:5863:4: ( ruleQualifiedName )
            {
            // InternalDSLPolicies.g:5863:4: ( ruleQualifiedName )
            // InternalDSLPolicies.g:5864:5: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getXTypeLiteralRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXTypeLiteralAccess().getTypeJvmTypeCrossReference_3_0());
              				
            }
            pushFollow(FOLLOW_79);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalDSLPolicies.g:5878:3: ( (lv_arrayDimensions_4_0= ruleArrayBrackets ) )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( (LA98_0==74) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // InternalDSLPolicies.g:5879:4: (lv_arrayDimensions_4_0= ruleArrayBrackets )
            	    {
            	    // InternalDSLPolicies.g:5879:4: (lv_arrayDimensions_4_0= ruleArrayBrackets )
            	    // InternalDSLPolicies.g:5880:5: lv_arrayDimensions_4_0= ruleArrayBrackets
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getXTypeLiteralAccess().getArrayDimensionsArrayBracketsParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_79);
            	    lv_arrayDimensions_4_0=ruleArrayBrackets();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getXTypeLiteralRule());
            	      					}
            	      					add(
            	      						current,
            	      						"arrayDimensions",
            	      						lv_arrayDimensions_4_0,
            	      						"org.eclipse.xtext.xbase.Xtype.ArrayBrackets");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop98;
                }
            } while (true);

            otherlv_5=(Token)match(input,24,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getXTypeLiteralAccess().getRightParenthesisKeyword_5());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXTypeLiteral"


    // $ANTLR start "entryRuleXThrowExpression"
    // InternalDSLPolicies.g:5905:1: entryRuleXThrowExpression returns [EObject current=null] : iv_ruleXThrowExpression= ruleXThrowExpression EOF ;
    public final EObject entryRuleXThrowExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXThrowExpression = null;


        try {
            // InternalDSLPolicies.g:5905:57: (iv_ruleXThrowExpression= ruleXThrowExpression EOF )
            // InternalDSLPolicies.g:5906:2: iv_ruleXThrowExpression= ruleXThrowExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXThrowExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXThrowExpression=ruleXThrowExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXThrowExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXThrowExpression"


    // $ANTLR start "ruleXThrowExpression"
    // InternalDSLPolicies.g:5912:1: ruleXThrowExpression returns [EObject current=null] : ( () otherlv_1= 'throw' ( (lv_expression_2_0= ruleXExpression ) ) ) ;
    public final EObject ruleXThrowExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_expression_2_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:5918:2: ( ( () otherlv_1= 'throw' ( (lv_expression_2_0= ruleXExpression ) ) ) )
            // InternalDSLPolicies.g:5919:2: ( () otherlv_1= 'throw' ( (lv_expression_2_0= ruleXExpression ) ) )
            {
            // InternalDSLPolicies.g:5919:2: ( () otherlv_1= 'throw' ( (lv_expression_2_0= ruleXExpression ) ) )
            // InternalDSLPolicies.g:5920:3: () otherlv_1= 'throw' ( (lv_expression_2_0= ruleXExpression ) )
            {
            // InternalDSLPolicies.g:5920:3: ()
            // InternalDSLPolicies.g:5921:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXThrowExpressionAccess().getXThrowExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,98,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXThrowExpressionAccess().getThrowKeyword_1());
              		
            }
            // InternalDSLPolicies.g:5931:3: ( (lv_expression_2_0= ruleXExpression ) )
            // InternalDSLPolicies.g:5932:4: (lv_expression_2_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:5932:4: (lv_expression_2_0= ruleXExpression )
            // InternalDSLPolicies.g:5933:5: lv_expression_2_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXThrowExpressionAccess().getExpressionXExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_expression_2_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXThrowExpressionRule());
              					}
              					set(
              						current,
              						"expression",
              						lv_expression_2_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXThrowExpression"


    // $ANTLR start "entryRuleXReturnExpression"
    // InternalDSLPolicies.g:5954:1: entryRuleXReturnExpression returns [EObject current=null] : iv_ruleXReturnExpression= ruleXReturnExpression EOF ;
    public final EObject entryRuleXReturnExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXReturnExpression = null;


        try {
            // InternalDSLPolicies.g:5954:58: (iv_ruleXReturnExpression= ruleXReturnExpression EOF )
            // InternalDSLPolicies.g:5955:2: iv_ruleXReturnExpression= ruleXReturnExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXReturnExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXReturnExpression=ruleXReturnExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXReturnExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXReturnExpression"


    // $ANTLR start "ruleXReturnExpression"
    // InternalDSLPolicies.g:5961:1: ruleXReturnExpression returns [EObject current=null] : ( () otherlv_1= 'return' ( ( 'extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING )=> (lv_expression_2_0= ruleXExpression ) )? ) ;
    public final EObject ruleXReturnExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_expression_2_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:5967:2: ( ( () otherlv_1= 'return' ( ( 'extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING )=> (lv_expression_2_0= ruleXExpression ) )? ) )
            // InternalDSLPolicies.g:5968:2: ( () otherlv_1= 'return' ( ( 'extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING )=> (lv_expression_2_0= ruleXExpression ) )? )
            {
            // InternalDSLPolicies.g:5968:2: ( () otherlv_1= 'return' ( ( 'extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING )=> (lv_expression_2_0= ruleXExpression ) )? )
            // InternalDSLPolicies.g:5969:3: () otherlv_1= 'return' ( ( 'extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING )=> (lv_expression_2_0= ruleXExpression ) )?
            {
            // InternalDSLPolicies.g:5969:3: ()
            // InternalDSLPolicies.g:5970:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXReturnExpressionAccess().getXReturnExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,99,FOLLOW_80); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXReturnExpressionAccess().getReturnKeyword_1());
              		
            }
            // InternalDSLPolicies.g:5980:3: ( ( 'extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING )=> (lv_expression_2_0= ruleXExpression ) )?
            int alt99=2;
            alt99 = dfa99.predict(input);
            switch (alt99) {
                case 1 :
                    // InternalDSLPolicies.g:5981:4: ( 'extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING )=> (lv_expression_2_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:5982:4: (lv_expression_2_0= ruleXExpression )
                    // InternalDSLPolicies.g:5983:5: lv_expression_2_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getXReturnExpressionAccess().getExpressionXExpressionParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_2_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getXReturnExpressionRule());
                      					}
                      					set(
                      						current,
                      						"expression",
                      						lv_expression_2_0,
                      						"org.eclipse.xtext.xbase.Xbase.XExpression");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXReturnExpression"


    // $ANTLR start "entryRuleXTryCatchFinallyExpression"
    // InternalDSLPolicies.g:6004:1: entryRuleXTryCatchFinallyExpression returns [EObject current=null] : iv_ruleXTryCatchFinallyExpression= ruleXTryCatchFinallyExpression EOF ;
    public final EObject entryRuleXTryCatchFinallyExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXTryCatchFinallyExpression = null;


        try {
            // InternalDSLPolicies.g:6004:67: (iv_ruleXTryCatchFinallyExpression= ruleXTryCatchFinallyExpression EOF )
            // InternalDSLPolicies.g:6005:2: iv_ruleXTryCatchFinallyExpression= ruleXTryCatchFinallyExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXTryCatchFinallyExpression=ruleXTryCatchFinallyExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXTryCatchFinallyExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXTryCatchFinallyExpression"


    // $ANTLR start "ruleXTryCatchFinallyExpression"
    // InternalDSLPolicies.g:6011:1: ruleXTryCatchFinallyExpression returns [EObject current=null] : ( () otherlv_1= 'try' ( (lv_expression_2_0= ruleXExpression ) ) ( ( ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+ ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )? ) | (otherlv_6= 'finally' ( (lv_finallyExpression_7_0= ruleXExpression ) ) ) ) ) ;
    public final EObject ruleXTryCatchFinallyExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_expression_2_0 = null;

        EObject lv_catchClauses_3_0 = null;

        EObject lv_finallyExpression_5_0 = null;

        EObject lv_finallyExpression_7_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6017:2: ( ( () otherlv_1= 'try' ( (lv_expression_2_0= ruleXExpression ) ) ( ( ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+ ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )? ) | (otherlv_6= 'finally' ( (lv_finallyExpression_7_0= ruleXExpression ) ) ) ) ) )
            // InternalDSLPolicies.g:6018:2: ( () otherlv_1= 'try' ( (lv_expression_2_0= ruleXExpression ) ) ( ( ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+ ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )? ) | (otherlv_6= 'finally' ( (lv_finallyExpression_7_0= ruleXExpression ) ) ) ) )
            {
            // InternalDSLPolicies.g:6018:2: ( () otherlv_1= 'try' ( (lv_expression_2_0= ruleXExpression ) ) ( ( ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+ ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )? ) | (otherlv_6= 'finally' ( (lv_finallyExpression_7_0= ruleXExpression ) ) ) ) )
            // InternalDSLPolicies.g:6019:3: () otherlv_1= 'try' ( (lv_expression_2_0= ruleXExpression ) ) ( ( ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+ ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )? ) | (otherlv_6= 'finally' ( (lv_finallyExpression_7_0= ruleXExpression ) ) ) )
            {
            // InternalDSLPolicies.g:6019:3: ()
            // InternalDSLPolicies.g:6020:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getXTryCatchFinallyExpressionAccess().getXTryCatchFinallyExpressionAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,100,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXTryCatchFinallyExpressionAccess().getTryKeyword_1());
              		
            }
            // InternalDSLPolicies.g:6030:3: ( (lv_expression_2_0= ruleXExpression ) )
            // InternalDSLPolicies.g:6031:4: (lv_expression_2_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:6031:4: (lv_expression_2_0= ruleXExpression )
            // InternalDSLPolicies.g:6032:5: lv_expression_2_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionAccess().getExpressionXExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_81);
            lv_expression_2_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXTryCatchFinallyExpressionRule());
              					}
              					set(
              						current,
              						"expression",
              						lv_expression_2_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalDSLPolicies.g:6049:3: ( ( ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+ ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )? ) | (otherlv_6= 'finally' ( (lv_finallyExpression_7_0= ruleXExpression ) ) ) )
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==103) ) {
                alt102=1;
            }
            else if ( (LA102_0==101) ) {
                alt102=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }
            switch (alt102) {
                case 1 :
                    // InternalDSLPolicies.g:6050:4: ( ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+ ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )? )
                    {
                    // InternalDSLPolicies.g:6050:4: ( ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+ ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )? )
                    // InternalDSLPolicies.g:6051:5: ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+ ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )?
                    {
                    // InternalDSLPolicies.g:6051:5: ( ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause ) )+
                    int cnt100=0;
                    loop100:
                    do {
                        int alt100=2;
                        int LA100_0 = input.LA(1);

                        if ( (LA100_0==103) ) {
                            int LA100_2 = input.LA(2);

                            if ( (synpred38_InternalDSLPolicies()) ) {
                                alt100=1;
                            }


                        }


                        switch (alt100) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:6052:6: ( 'catch' )=> (lv_catchClauses_3_0= ruleXCatchClause )
                    	    {
                    	    // InternalDSLPolicies.g:6053:6: (lv_catchClauses_3_0= ruleXCatchClause )
                    	    // InternalDSLPolicies.g:6054:7: lv_catchClauses_3_0= ruleXCatchClause
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionAccess().getCatchClausesXCatchClauseParserRuleCall_3_0_0_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_82);
                    	    lv_catchClauses_3_0=ruleXCatchClause();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getXTryCatchFinallyExpressionRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"catchClauses",
                    	      								lv_catchClauses_3_0,
                    	      								"org.eclipse.xtext.xbase.Xbase.XCatchClause");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt100 >= 1 ) break loop100;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(100, input);
                                throw eee;
                        }
                        cnt100++;
                    } while (true);

                    // InternalDSLPolicies.g:6071:5: ( ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) ) )?
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( (LA101_0==101) ) {
                        int LA101_1 = input.LA(2);

                        if ( (synpred39_InternalDSLPolicies()) ) {
                            alt101=1;
                        }
                    }
                    switch (alt101) {
                        case 1 :
                            // InternalDSLPolicies.g:6072:6: ( ( 'finally' )=>otherlv_4= 'finally' ) ( (lv_finallyExpression_5_0= ruleXExpression ) )
                            {
                            // InternalDSLPolicies.g:6072:6: ( ( 'finally' )=>otherlv_4= 'finally' )
                            // InternalDSLPolicies.g:6073:7: ( 'finally' )=>otherlv_4= 'finally'
                            {
                            otherlv_4=(Token)match(input,101,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(otherlv_4, grammarAccess.getXTryCatchFinallyExpressionAccess().getFinallyKeyword_3_0_1_0());
                              						
                            }

                            }

                            // InternalDSLPolicies.g:6079:6: ( (lv_finallyExpression_5_0= ruleXExpression ) )
                            // InternalDSLPolicies.g:6080:7: (lv_finallyExpression_5_0= ruleXExpression )
                            {
                            // InternalDSLPolicies.g:6080:7: (lv_finallyExpression_5_0= ruleXExpression )
                            // InternalDSLPolicies.g:6081:8: lv_finallyExpression_5_0= ruleXExpression
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionAccess().getFinallyExpressionXExpressionParserRuleCall_3_0_1_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_finallyExpression_5_0=ruleXExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getXTryCatchFinallyExpressionRule());
                              								}
                              								set(
                              									current,
                              									"finallyExpression",
                              									lv_finallyExpression_5_0,
                              									"org.eclipse.xtext.xbase.Xbase.XExpression");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:6101:4: (otherlv_6= 'finally' ( (lv_finallyExpression_7_0= ruleXExpression ) ) )
                    {
                    // InternalDSLPolicies.g:6101:4: (otherlv_6= 'finally' ( (lv_finallyExpression_7_0= ruleXExpression ) ) )
                    // InternalDSLPolicies.g:6102:5: otherlv_6= 'finally' ( (lv_finallyExpression_7_0= ruleXExpression ) )
                    {
                    otherlv_6=(Token)match(input,101,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_6, grammarAccess.getXTryCatchFinallyExpressionAccess().getFinallyKeyword_3_1_0());
                      				
                    }
                    // InternalDSLPolicies.g:6106:5: ( (lv_finallyExpression_7_0= ruleXExpression ) )
                    // InternalDSLPolicies.g:6107:6: (lv_finallyExpression_7_0= ruleXExpression )
                    {
                    // InternalDSLPolicies.g:6107:6: (lv_finallyExpression_7_0= ruleXExpression )
                    // InternalDSLPolicies.g:6108:7: lv_finallyExpression_7_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionAccess().getFinallyExpressionXExpressionParserRuleCall_3_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_finallyExpression_7_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getXTryCatchFinallyExpressionRule());
                      							}
                      							set(
                      								current,
                      								"finallyExpression",
                      								lv_finallyExpression_7_0,
                      								"org.eclipse.xtext.xbase.Xbase.XExpression");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXTryCatchFinallyExpression"


    // $ANTLR start "entryRuleXSynchronizedExpression"
    // InternalDSLPolicies.g:6131:1: entryRuleXSynchronizedExpression returns [EObject current=null] : iv_ruleXSynchronizedExpression= ruleXSynchronizedExpression EOF ;
    public final EObject entryRuleXSynchronizedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXSynchronizedExpression = null;


        try {
            // InternalDSLPolicies.g:6131:64: (iv_ruleXSynchronizedExpression= ruleXSynchronizedExpression EOF )
            // InternalDSLPolicies.g:6132:2: iv_ruleXSynchronizedExpression= ruleXSynchronizedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXSynchronizedExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXSynchronizedExpression=ruleXSynchronizedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXSynchronizedExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXSynchronizedExpression"


    // $ANTLR start "ruleXSynchronizedExpression"
    // InternalDSLPolicies.g:6138:1: ruleXSynchronizedExpression returns [EObject current=null] : ( ( ( ( () 'synchronized' '(' ) )=> ( () otherlv_1= 'synchronized' otherlv_2= '(' ) ) ( (lv_param_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_expression_5_0= ruleXExpression ) ) ) ;
    public final EObject ruleXSynchronizedExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_param_3_0 = null;

        EObject lv_expression_5_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6144:2: ( ( ( ( ( () 'synchronized' '(' ) )=> ( () otherlv_1= 'synchronized' otherlv_2= '(' ) ) ( (lv_param_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_expression_5_0= ruleXExpression ) ) ) )
            // InternalDSLPolicies.g:6145:2: ( ( ( ( () 'synchronized' '(' ) )=> ( () otherlv_1= 'synchronized' otherlv_2= '(' ) ) ( (lv_param_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_expression_5_0= ruleXExpression ) ) )
            {
            // InternalDSLPolicies.g:6145:2: ( ( ( ( () 'synchronized' '(' ) )=> ( () otherlv_1= 'synchronized' otherlv_2= '(' ) ) ( (lv_param_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_expression_5_0= ruleXExpression ) ) )
            // InternalDSLPolicies.g:6146:3: ( ( ( () 'synchronized' '(' ) )=> ( () otherlv_1= 'synchronized' otherlv_2= '(' ) ) ( (lv_param_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_expression_5_0= ruleXExpression ) )
            {
            // InternalDSLPolicies.g:6146:3: ( ( ( () 'synchronized' '(' ) )=> ( () otherlv_1= 'synchronized' otherlv_2= '(' ) )
            // InternalDSLPolicies.g:6147:4: ( ( () 'synchronized' '(' ) )=> ( () otherlv_1= 'synchronized' otherlv_2= '(' )
            {
            // InternalDSLPolicies.g:6154:4: ( () otherlv_1= 'synchronized' otherlv_2= '(' )
            // InternalDSLPolicies.g:6155:5: () otherlv_1= 'synchronized' otherlv_2= '('
            {
            // InternalDSLPolicies.g:6155:5: ()
            // InternalDSLPolicies.g:6156:6: 
            {
            if ( state.backtracking==0 ) {

              						current = forceCreateModelElement(
              							grammarAccess.getXSynchronizedExpressionAccess().getXSynchronizedExpressionAction_0_0_0(),
              							current);
              					
            }

            }

            otherlv_1=(Token)match(input,102,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getXSynchronizedExpressionAccess().getSynchronizedKeyword_0_0_1());
              				
            }
            otherlv_2=(Token)match(input,23,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_2, grammarAccess.getXSynchronizedExpressionAccess().getLeftParenthesisKeyword_0_0_2());
              				
            }

            }


            }

            // InternalDSLPolicies.g:6172:3: ( (lv_param_3_0= ruleXExpression ) )
            // InternalDSLPolicies.g:6173:4: (lv_param_3_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:6173:4: (lv_param_3_0= ruleXExpression )
            // InternalDSLPolicies.g:6174:5: lv_param_3_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXSynchronizedExpressionAccess().getParamXExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_param_3_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXSynchronizedExpressionRule());
              					}
              					set(
              						current,
              						"param",
              						lv_param_3_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_4=(Token)match(input,24,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getXSynchronizedExpressionAccess().getRightParenthesisKeyword_2());
              		
            }
            // InternalDSLPolicies.g:6195:3: ( (lv_expression_5_0= ruleXExpression ) )
            // InternalDSLPolicies.g:6196:4: (lv_expression_5_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:6196:4: (lv_expression_5_0= ruleXExpression )
            // InternalDSLPolicies.g:6197:5: lv_expression_5_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXSynchronizedExpressionAccess().getExpressionXExpressionParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_expression_5_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXSynchronizedExpressionRule());
              					}
              					set(
              						current,
              						"expression",
              						lv_expression_5_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXSynchronizedExpression"


    // $ANTLR start "entryRuleXCatchClause"
    // InternalDSLPolicies.g:6218:1: entryRuleXCatchClause returns [EObject current=null] : iv_ruleXCatchClause= ruleXCatchClause EOF ;
    public final EObject entryRuleXCatchClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXCatchClause = null;


        try {
            // InternalDSLPolicies.g:6218:53: (iv_ruleXCatchClause= ruleXCatchClause EOF )
            // InternalDSLPolicies.g:6219:2: iv_ruleXCatchClause= ruleXCatchClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXCatchClauseRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXCatchClause=ruleXCatchClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXCatchClause; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXCatchClause"


    // $ANTLR start "ruleXCatchClause"
    // InternalDSLPolicies.g:6225:1: ruleXCatchClause returns [EObject current=null] : ( ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) ) ) ;
    public final EObject ruleXCatchClause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_declaredParam_2_0 = null;

        EObject lv_expression_4_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6231:2: ( ( ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) ) ) )
            // InternalDSLPolicies.g:6232:2: ( ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) ) )
            {
            // InternalDSLPolicies.g:6232:2: ( ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) ) )
            // InternalDSLPolicies.g:6233:3: ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) )
            {
            // InternalDSLPolicies.g:6233:3: ( ( 'catch' )=>otherlv_0= 'catch' )
            // InternalDSLPolicies.g:6234:4: ( 'catch' )=>otherlv_0= 'catch'
            {
            otherlv_0=(Token)match(input,103,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_0, grammarAccess.getXCatchClauseAccess().getCatchKeyword_0());
              			
            }

            }

            otherlv_1=(Token)match(input,23,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getXCatchClauseAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalDSLPolicies.g:6244:3: ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) )
            // InternalDSLPolicies.g:6245:4: (lv_declaredParam_2_0= ruleFullJvmFormalParameter )
            {
            // InternalDSLPolicies.g:6245:4: (lv_declaredParam_2_0= ruleFullJvmFormalParameter )
            // InternalDSLPolicies.g:6246:5: lv_declaredParam_2_0= ruleFullJvmFormalParameter
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXCatchClauseAccess().getDeclaredParamFullJvmFormalParameterParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_declaredParam_2_0=ruleFullJvmFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXCatchClauseRule());
              					}
              					set(
              						current,
              						"declaredParam",
              						lv_declaredParam_2_0,
              						"org.eclipse.xtext.xbase.Xbase.FullJvmFormalParameter");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getXCatchClauseAccess().getRightParenthesisKeyword_3());
              		
            }
            // InternalDSLPolicies.g:6267:3: ( (lv_expression_4_0= ruleXExpression ) )
            // InternalDSLPolicies.g:6268:4: (lv_expression_4_0= ruleXExpression )
            {
            // InternalDSLPolicies.g:6268:4: (lv_expression_4_0= ruleXExpression )
            // InternalDSLPolicies.g:6269:5: lv_expression_4_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXCatchClauseAccess().getExpressionXExpressionParserRuleCall_4_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_expression_4_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXCatchClauseRule());
              					}
              					set(
              						current,
              						"expression",
              						lv_expression_4_0,
              						"org.eclipse.xtext.xbase.Xbase.XExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXCatchClause"


    // $ANTLR start "entryRuleQualifiedName"
    // InternalDSLPolicies.g:6290:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalDSLPolicies.g:6290:53: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalDSLPolicies.g:6291:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifiedName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // InternalDSLPolicies.g:6297:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ValidID_0 = null;

        AntlrDatatypeRuleToken this_ValidID_2 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6303:2: ( (this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )* ) )
            // InternalDSLPolicies.g:6304:2: (this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )* )
            {
            // InternalDSLPolicies.g:6304:2: (this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )* )
            // InternalDSLPolicies.g:6305:3: this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_83);
            this_ValidID_0=ruleValidID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ValidID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalDSLPolicies.g:6315:3: ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )*
            loop103:
            do {
                int alt103=2;
                int LA103_0 = input.LA(1);

                if ( (LA103_0==67) ) {
                    int LA103_2 = input.LA(2);

                    if ( (LA103_2==RULE_ID) ) {
                        int LA103_3 = input.LA(3);

                        if ( (synpred42_InternalDSLPolicies()) ) {
                            alt103=1;
                        }


                    }


                }


                switch (alt103) {
            	case 1 :
            	    // InternalDSLPolicies.g:6316:4: ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID
            	    {
            	    // InternalDSLPolicies.g:6316:4: ( ( '.' )=>kw= '.' )
            	    // InternalDSLPolicies.g:6317:5: ( '.' )=>kw= '.'
            	    {
            	    kw=(Token)match(input,67,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					current.merge(kw);
            	      					newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0());
            	      				
            	    }

            	    }

            	    if ( state.backtracking==0 ) {

            	      				newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_1_1());
            	      			
            	    }
            	    pushFollow(FOLLOW_83);
            	    this_ValidID_2=ruleValidID();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_ValidID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				afterParserOrEnumRuleCall();
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop103;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleNumber"
    // InternalDSLPolicies.g:6339:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;



        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalDSLPolicies.g:6341:2: (iv_ruleNumber= ruleNumber EOF )
            // InternalDSLPolicies.g:6342:2: iv_ruleNumber= ruleNumber EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNumber=ruleNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNumber.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRuleNumber"


    // $ANTLR start "ruleNumber"
    // InternalDSLPolicies.g:6351:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_HEX_0= RULE_HEX | ( (this_INT_1= RULE_INT | this_DECIMAL_2= RULE_DECIMAL ) (kw= '.' (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_HEX_0=null;
        Token this_INT_1=null;
        Token this_DECIMAL_2=null;
        Token kw=null;
        Token this_INT_4=null;
        Token this_DECIMAL_5=null;


        	enterRule();
        	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();

        try {
            // InternalDSLPolicies.g:6358:2: ( (this_HEX_0= RULE_HEX | ( (this_INT_1= RULE_INT | this_DECIMAL_2= RULE_DECIMAL ) (kw= '.' (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) ) )
            // InternalDSLPolicies.g:6359:2: (this_HEX_0= RULE_HEX | ( (this_INT_1= RULE_INT | this_DECIMAL_2= RULE_DECIMAL ) (kw= '.' (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) )
            {
            // InternalDSLPolicies.g:6359:2: (this_HEX_0= RULE_HEX | ( (this_INT_1= RULE_INT | this_DECIMAL_2= RULE_DECIMAL ) (kw= '.' (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? ) )
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==RULE_HEX) ) {
                alt107=1;
            }
            else if ( (LA107_0==RULE_INT||LA107_0==RULE_DECIMAL) ) {
                alt107=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }
            switch (alt107) {
                case 1 :
                    // InternalDSLPolicies.g:6360:3: this_HEX_0= RULE_HEX
                    {
                    this_HEX_0=(Token)match(input,RULE_HEX,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_HEX_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_HEX_0, grammarAccess.getNumberAccess().getHEXTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:6368:3: ( (this_INT_1= RULE_INT | this_DECIMAL_2= RULE_DECIMAL ) (kw= '.' (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? )
                    {
                    // InternalDSLPolicies.g:6368:3: ( (this_INT_1= RULE_INT | this_DECIMAL_2= RULE_DECIMAL ) (kw= '.' (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )? )
                    // InternalDSLPolicies.g:6369:4: (this_INT_1= RULE_INT | this_DECIMAL_2= RULE_DECIMAL ) (kw= '.' (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )?
                    {
                    // InternalDSLPolicies.g:6369:4: (this_INT_1= RULE_INT | this_DECIMAL_2= RULE_DECIMAL )
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==RULE_INT) ) {
                        alt104=1;
                    }
                    else if ( (LA104_0==RULE_DECIMAL) ) {
                        alt104=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 104, 0, input);

                        throw nvae;
                    }
                    switch (alt104) {
                        case 1 :
                            // InternalDSLPolicies.g:6370:5: this_INT_1= RULE_INT
                            {
                            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_83); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_INT_1);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_INT_1, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_0_0());
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalDSLPolicies.g:6378:5: this_DECIMAL_2= RULE_DECIMAL
                            {
                            this_DECIMAL_2=(Token)match(input,RULE_DECIMAL,FOLLOW_83); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_DECIMAL_2);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_DECIMAL_2, grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_0_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalDSLPolicies.g:6386:4: (kw= '.' (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL ) )?
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==67) ) {
                        int LA106_1 = input.LA(2);

                        if ( (LA106_1==RULE_INT||LA106_1==RULE_DECIMAL) ) {
                            alt106=1;
                        }
                    }
                    switch (alt106) {
                        case 1 :
                            // InternalDSLPolicies.g:6387:5: kw= '.' (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL )
                            {
                            kw=(Token)match(input,67,FOLLOW_84); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_1_1_0());
                              				
                            }
                            // InternalDSLPolicies.g:6392:5: (this_INT_4= RULE_INT | this_DECIMAL_5= RULE_DECIMAL )
                            int alt105=2;
                            int LA105_0 = input.LA(1);

                            if ( (LA105_0==RULE_INT) ) {
                                alt105=1;
                            }
                            else if ( (LA105_0==RULE_DECIMAL) ) {
                                alt105=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 105, 0, input);

                                throw nvae;
                            }
                            switch (alt105) {
                                case 1 :
                                    // InternalDSLPolicies.g:6393:6: this_INT_4= RULE_INT
                                    {
                                    this_INT_4=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						current.merge(this_INT_4);
                                      					
                                    }
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(this_INT_4, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_1_1_0());
                                      					
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalDSLPolicies.g:6401:6: this_DECIMAL_5= RULE_DECIMAL
                                    {
                                    this_DECIMAL_5=(Token)match(input,RULE_DECIMAL,FOLLOW_2); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						current.merge(this_DECIMAL_5);
                                      					
                                    }
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(this_DECIMAL_5, grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_1_1_1());
                                      					
                                    }

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myHiddenTokenState.restore();

        }
        return current;
    }
    // $ANTLR end "ruleNumber"


    // $ANTLR start "entryRuleJvmTypeReference"
    // InternalDSLPolicies.g:6418:1: entryRuleJvmTypeReference returns [EObject current=null] : iv_ruleJvmTypeReference= ruleJvmTypeReference EOF ;
    public final EObject entryRuleJvmTypeReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmTypeReference = null;


        try {
            // InternalDSLPolicies.g:6418:57: (iv_ruleJvmTypeReference= ruleJvmTypeReference EOF )
            // InternalDSLPolicies.g:6419:2: iv_ruleJvmTypeReference= ruleJvmTypeReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmTypeReferenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJvmTypeReference=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmTypeReference; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJvmTypeReference"


    // $ANTLR start "ruleJvmTypeReference"
    // InternalDSLPolicies.g:6425:1: ruleJvmTypeReference returns [EObject current=null] : ( (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* ) | this_XFunctionTypeRef_3= ruleXFunctionTypeRef ) ;
    public final EObject ruleJvmTypeReference() throws RecognitionException {
        EObject current = null;

        EObject this_JvmParameterizedTypeReference_0 = null;

        EObject this_XFunctionTypeRef_3 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6431:2: ( ( (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* ) | this_XFunctionTypeRef_3= ruleXFunctionTypeRef ) )
            // InternalDSLPolicies.g:6432:2: ( (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* ) | this_XFunctionTypeRef_3= ruleXFunctionTypeRef )
            {
            // InternalDSLPolicies.g:6432:2: ( (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* ) | this_XFunctionTypeRef_3= ruleXFunctionTypeRef )
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==RULE_ID) ) {
                alt109=1;
            }
            else if ( (LA109_0==23||LA109_0==54) ) {
                alt109=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 109, 0, input);

                throw nvae;
            }
            switch (alt109) {
                case 1 :
                    // InternalDSLPolicies.g:6433:3: (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* )
                    {
                    // InternalDSLPolicies.g:6433:3: (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* )
                    // InternalDSLPolicies.g:6434:4: this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )*
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getJvmTypeReferenceAccess().getJvmParameterizedTypeReferenceParserRuleCall_0_0());
                      			
                    }
                    pushFollow(FOLLOW_77);
                    this_JvmParameterizedTypeReference_0=ruleJvmParameterizedTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_JvmParameterizedTypeReference_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    // InternalDSLPolicies.g:6442:4: ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )*
                    loop108:
                    do {
                        int alt108=2;
                        int LA108_0 = input.LA(1);

                        if ( (LA108_0==74) ) {
                            int LA108_2 = input.LA(2);

                            if ( (LA108_2==75) ) {
                                int LA108_3 = input.LA(3);

                                if ( (synpred43_InternalDSLPolicies()) ) {
                                    alt108=1;
                                }


                            }


                        }


                        switch (alt108) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:6443:5: ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets )
                    	    {
                    	    // InternalDSLPolicies.g:6449:5: ( () ruleArrayBrackets )
                    	    // InternalDSLPolicies.g:6450:6: () ruleArrayBrackets
                    	    {
                    	    // InternalDSLPolicies.g:6450:6: ()
                    	    // InternalDSLPolicies.g:6451:7: 
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							current = forceCreateModelElementAndSet(
                    	      								grammarAccess.getJvmTypeReferenceAccess().getJvmGenericArrayTypeReferenceComponentTypeAction_0_1_0_0(),
                    	      								current);
                    	      						
                    	    }

                    	    }

                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getJvmTypeReferenceAccess().getArrayBracketsParserRuleCall_0_1_0_1());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_77);
                    	    ruleArrayBrackets();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop108;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:6468:3: this_XFunctionTypeRef_3= ruleXFunctionTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getJvmTypeReferenceAccess().getXFunctionTypeRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_XFunctionTypeRef_3=ruleXFunctionTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_XFunctionTypeRef_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJvmTypeReference"


    // $ANTLR start "entryRuleArrayBrackets"
    // InternalDSLPolicies.g:6480:1: entryRuleArrayBrackets returns [String current=null] : iv_ruleArrayBrackets= ruleArrayBrackets EOF ;
    public final String entryRuleArrayBrackets() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleArrayBrackets = null;


        try {
            // InternalDSLPolicies.g:6480:53: (iv_ruleArrayBrackets= ruleArrayBrackets EOF )
            // InternalDSLPolicies.g:6481:2: iv_ruleArrayBrackets= ruleArrayBrackets EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrayBracketsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArrayBrackets=ruleArrayBrackets();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArrayBrackets.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArrayBrackets"


    // $ANTLR start "ruleArrayBrackets"
    // InternalDSLPolicies.g:6487:1: ruleArrayBrackets returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' kw= ']' ) ;
    public final AntlrDatatypeRuleToken ruleArrayBrackets() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:6493:2: ( (kw= '[' kw= ']' ) )
            // InternalDSLPolicies.g:6494:2: (kw= '[' kw= ']' )
            {
            // InternalDSLPolicies.g:6494:2: (kw= '[' kw= ']' )
            // InternalDSLPolicies.g:6495:3: kw= '[' kw= ']'
            {
            kw=(Token)match(input,74,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getArrayBracketsAccess().getLeftSquareBracketKeyword_0());
              		
            }
            kw=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getArrayBracketsAccess().getRightSquareBracketKeyword_1());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArrayBrackets"


    // $ANTLR start "entryRuleXFunctionTypeRef"
    // InternalDSLPolicies.g:6509:1: entryRuleXFunctionTypeRef returns [EObject current=null] : iv_ruleXFunctionTypeRef= ruleXFunctionTypeRef EOF ;
    public final EObject entryRuleXFunctionTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXFunctionTypeRef = null;


        try {
            // InternalDSLPolicies.g:6509:57: (iv_ruleXFunctionTypeRef= ruleXFunctionTypeRef EOF )
            // InternalDSLPolicies.g:6510:2: iv_ruleXFunctionTypeRef= ruleXFunctionTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXFunctionTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXFunctionTypeRef=ruleXFunctionTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXFunctionTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXFunctionTypeRef"


    // $ANTLR start "ruleXFunctionTypeRef"
    // InternalDSLPolicies.g:6516:1: ruleXFunctionTypeRef returns [EObject current=null] : ( (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleXFunctionTypeRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        EObject lv_paramTypes_1_0 = null;

        EObject lv_paramTypes_3_0 = null;

        EObject lv_returnType_6_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6522:2: ( ( (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) ) ) )
            // InternalDSLPolicies.g:6523:2: ( (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) ) )
            {
            // InternalDSLPolicies.g:6523:2: ( (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) ) )
            // InternalDSLPolicies.g:6524:3: (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) )
            {
            // InternalDSLPolicies.g:6524:3: (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )?
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==23) ) {
                alt112=1;
            }
            switch (alt112) {
                case 1 :
                    // InternalDSLPolicies.g:6525:4: otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')'
                    {
                    otherlv_0=(Token)match(input,23,FOLLOW_85); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getXFunctionTypeRefAccess().getLeftParenthesisKeyword_0_0());
                      			
                    }
                    // InternalDSLPolicies.g:6529:4: ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )?
                    int alt111=2;
                    int LA111_0 = input.LA(1);

                    if ( (LA111_0==RULE_ID||LA111_0==23||LA111_0==54) ) {
                        alt111=1;
                    }
                    switch (alt111) {
                        case 1 :
                            // InternalDSLPolicies.g:6530:5: ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )*
                            {
                            // InternalDSLPolicies.g:6530:5: ( (lv_paramTypes_1_0= ruleJvmTypeReference ) )
                            // InternalDSLPolicies.g:6531:6: (lv_paramTypes_1_0= ruleJvmTypeReference )
                            {
                            // InternalDSLPolicies.g:6531:6: (lv_paramTypes_1_0= ruleJvmTypeReference )
                            // InternalDSLPolicies.g:6532:7: lv_paramTypes_1_0= ruleJvmTypeReference
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getXFunctionTypeRefAccess().getParamTypesJvmTypeReferenceParserRuleCall_0_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_46);
                            lv_paramTypes_1_0=ruleJvmTypeReference();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getXFunctionTypeRefRule());
                              							}
                              							add(
                              								current,
                              								"paramTypes",
                              								lv_paramTypes_1_0,
                              								"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalDSLPolicies.g:6549:5: (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )*
                            loop110:
                            do {
                                int alt110=2;
                                int LA110_0 = input.LA(1);

                                if ( (LA110_0==70) ) {
                                    alt110=1;
                                }


                                switch (alt110) {
                            	case 1 :
                            	    // InternalDSLPolicies.g:6550:6: otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) )
                            	    {
                            	    otherlv_2=(Token)match(input,70,FOLLOW_30); if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      						newLeafNode(otherlv_2, grammarAccess.getXFunctionTypeRefAccess().getCommaKeyword_0_1_1_0());
                            	      					
                            	    }
                            	    // InternalDSLPolicies.g:6554:6: ( (lv_paramTypes_3_0= ruleJvmTypeReference ) )
                            	    // InternalDSLPolicies.g:6555:7: (lv_paramTypes_3_0= ruleJvmTypeReference )
                            	    {
                            	    // InternalDSLPolicies.g:6555:7: (lv_paramTypes_3_0= ruleJvmTypeReference )
                            	    // InternalDSLPolicies.g:6556:8: lv_paramTypes_3_0= ruleJvmTypeReference
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      								newCompositeNode(grammarAccess.getXFunctionTypeRefAccess().getParamTypesJvmTypeReferenceParserRuleCall_0_1_1_1_0());
                            	      							
                            	    }
                            	    pushFollow(FOLLOW_46);
                            	    lv_paramTypes_3_0=ruleJvmTypeReference();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      								if (current==null) {
                            	      									current = createModelElementForParent(grammarAccess.getXFunctionTypeRefRule());
                            	      								}
                            	      								add(
                            	      									current,
                            	      									"paramTypes",
                            	      									lv_paramTypes_3_0,
                            	      									"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
                            	      								afterParserOrEnumRuleCall();
                            	      							
                            	    }

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop110;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_4=(Token)match(input,24,FOLLOW_86); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getXFunctionTypeRefAccess().getRightParenthesisKeyword_0_2());
                      			
                    }

                    }
                    break;

            }

            otherlv_5=(Token)match(input,54,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getXFunctionTypeRefAccess().getEqualsSignGreaterThanSignKeyword_1());
              		
            }
            // InternalDSLPolicies.g:6584:3: ( (lv_returnType_6_0= ruleJvmTypeReference ) )
            // InternalDSLPolicies.g:6585:4: (lv_returnType_6_0= ruleJvmTypeReference )
            {
            // InternalDSLPolicies.g:6585:4: (lv_returnType_6_0= ruleJvmTypeReference )
            // InternalDSLPolicies.g:6586:5: lv_returnType_6_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getXFunctionTypeRefAccess().getReturnTypeJvmTypeReferenceParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_returnType_6_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getXFunctionTypeRefRule());
              					}
              					set(
              						current,
              						"returnType",
              						lv_returnType_6_0,
              						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXFunctionTypeRef"


    // $ANTLR start "entryRuleJvmParameterizedTypeReference"
    // InternalDSLPolicies.g:6607:1: entryRuleJvmParameterizedTypeReference returns [EObject current=null] : iv_ruleJvmParameterizedTypeReference= ruleJvmParameterizedTypeReference EOF ;
    public final EObject entryRuleJvmParameterizedTypeReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmParameterizedTypeReference = null;


        try {
            // InternalDSLPolicies.g:6607:70: (iv_ruleJvmParameterizedTypeReference= ruleJvmParameterizedTypeReference EOF )
            // InternalDSLPolicies.g:6608:2: iv_ruleJvmParameterizedTypeReference= ruleJvmParameterizedTypeReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJvmParameterizedTypeReference=ruleJvmParameterizedTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmParameterizedTypeReference; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJvmParameterizedTypeReference"


    // $ANTLR start "ruleJvmParameterizedTypeReference"
    // InternalDSLPolicies.g:6614:1: ruleJvmParameterizedTypeReference returns [EObject current=null] : ( ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )? ) ;
    public final EObject ruleJvmParameterizedTypeReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        EObject lv_arguments_2_0 = null;

        EObject lv_arguments_4_0 = null;

        EObject lv_arguments_10_0 = null;

        EObject lv_arguments_12_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6620:2: ( ( ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )? ) )
            // InternalDSLPolicies.g:6621:2: ( ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )? )
            {
            // InternalDSLPolicies.g:6621:2: ( ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )? )
            // InternalDSLPolicies.g:6622:3: ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )?
            {
            // InternalDSLPolicies.g:6622:3: ( ( ruleQualifiedName ) )
            // InternalDSLPolicies.g:6623:4: ( ruleQualifiedName )
            {
            // InternalDSLPolicies.g:6623:4: ( ruleQualifiedName )
            // InternalDSLPolicies.g:6624:5: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getJvmParameterizedTypeReferenceRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getTypeJvmTypeCrossReference_0_0());
              				
            }
            pushFollow(FOLLOW_87);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalDSLPolicies.g:6638:3: ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )?
            int alt117=2;
            alt117 = dfa117.predict(input);
            switch (alt117) {
                case 1 :
                    // InternalDSLPolicies.g:6639:4: ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )*
                    {
                    // InternalDSLPolicies.g:6639:4: ( ( '<' )=>otherlv_1= '<' )
                    // InternalDSLPolicies.g:6640:5: ( '<' )=>otherlv_1= '<'
                    {
                    otherlv_1=(Token)match(input,41,FOLLOW_42); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_1, grammarAccess.getJvmParameterizedTypeReferenceAccess().getLessThanSignKeyword_1_0());
                      				
                    }

                    }

                    // InternalDSLPolicies.g:6646:4: ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) )
                    // InternalDSLPolicies.g:6647:5: (lv_arguments_2_0= ruleJvmArgumentTypeReference )
                    {
                    // InternalDSLPolicies.g:6647:5: (lv_arguments_2_0= ruleJvmArgumentTypeReference )
                    // InternalDSLPolicies.g:6648:6: lv_arguments_2_0= ruleJvmArgumentTypeReference
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_43);
                    lv_arguments_2_0=ruleJvmArgumentTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
                      						}
                      						add(
                      							current,
                      							"arguments",
                      							lv_arguments_2_0,
                      							"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalDSLPolicies.g:6665:4: (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )*
                    loop113:
                    do {
                        int alt113=2;
                        int LA113_0 = input.LA(1);

                        if ( (LA113_0==70) ) {
                            alt113=1;
                        }


                        switch (alt113) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:6666:5: otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) )
                    	    {
                    	    otherlv_3=(Token)match(input,70,FOLLOW_42); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getJvmParameterizedTypeReferenceAccess().getCommaKeyword_1_2_0());
                    	      				
                    	    }
                    	    // InternalDSLPolicies.g:6670:5: ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) )
                    	    // InternalDSLPolicies.g:6671:6: (lv_arguments_4_0= ruleJvmArgumentTypeReference )
                    	    {
                    	    // InternalDSLPolicies.g:6671:6: (lv_arguments_4_0= ruleJvmArgumentTypeReference )
                    	    // InternalDSLPolicies.g:6672:7: lv_arguments_4_0= ruleJvmArgumentTypeReference
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_2_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_43);
                    	    lv_arguments_4_0=ruleJvmArgumentTypeReference();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"arguments",
                    	      								lv_arguments_4_0,
                    	      								"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop113;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,42,FOLLOW_83); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getJvmParameterizedTypeReferenceAccess().getGreaterThanSignKeyword_1_3());
                      			
                    }
                    // InternalDSLPolicies.g:6694:4: ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )*
                    loop116:
                    do {
                        int alt116=2;
                        int LA116_0 = input.LA(1);

                        if ( (LA116_0==67) ) {
                            int LA116_2 = input.LA(2);

                            if ( (LA116_2==RULE_ID) ) {
                                int LA116_3 = input.LA(3);

                                if ( (synpred45_InternalDSLPolicies()) ) {
                                    alt116=1;
                                }


                            }


                        }


                        switch (alt116) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:6695:5: ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )?
                    	    {
                    	    // InternalDSLPolicies.g:6695:5: ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) )
                    	    // InternalDSLPolicies.g:6696:6: ( ( () '.' ) )=> ( () otherlv_7= '.' )
                    	    {
                    	    // InternalDSLPolicies.g:6702:6: ( () otherlv_7= '.' )
                    	    // InternalDSLPolicies.g:6703:7: () otherlv_7= '.'
                    	    {
                    	    // InternalDSLPolicies.g:6703:7: ()
                    	    // InternalDSLPolicies.g:6704:8: 
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      								current = forceCreateModelElementAndSet(
                    	      									grammarAccess.getJvmParameterizedTypeReferenceAccess().getJvmInnerTypeReferenceOuterAction_1_4_0_0_0(),
                    	      									current);
                    	      							
                    	    }

                    	    }

                    	    otherlv_7=(Token)match(input,67,FOLLOW_20); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							newLeafNode(otherlv_7, grammarAccess.getJvmParameterizedTypeReferenceAccess().getFullStopKeyword_1_4_0_0_1());
                    	      						
                    	    }

                    	    }


                    	    }

                    	    // InternalDSLPolicies.g:6716:5: ( ( ruleValidID ) )
                    	    // InternalDSLPolicies.g:6717:6: ( ruleValidID )
                    	    {
                    	    // InternalDSLPolicies.g:6717:6: ( ruleValidID )
                    	    // InternalDSLPolicies.g:6718:7: ruleValidID
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElement(grammarAccess.getJvmParameterizedTypeReferenceRule());
                    	      							}
                    	      						
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getTypeJvmTypeCrossReference_1_4_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_88);
                    	    ruleValidID();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }

                    	    // InternalDSLPolicies.g:6732:5: ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )?
                    	    int alt115=2;
                    	    alt115 = dfa115.predict(input);
                    	    switch (alt115) {
                    	        case 1 :
                    	            // InternalDSLPolicies.g:6733:6: ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>'
                    	            {
                    	            // InternalDSLPolicies.g:6733:6: ( ( '<' )=>otherlv_9= '<' )
                    	            // InternalDSLPolicies.g:6734:7: ( '<' )=>otherlv_9= '<'
                    	            {
                    	            otherlv_9=(Token)match(input,41,FOLLOW_42); if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              							newLeafNode(otherlv_9, grammarAccess.getJvmParameterizedTypeReferenceAccess().getLessThanSignKeyword_1_4_2_0());
                    	              						
                    	            }

                    	            }

                    	            // InternalDSLPolicies.g:6740:6: ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) )
                    	            // InternalDSLPolicies.g:6741:7: (lv_arguments_10_0= ruleJvmArgumentTypeReference )
                    	            {
                    	            // InternalDSLPolicies.g:6741:7: (lv_arguments_10_0= ruleJvmArgumentTypeReference )
                    	            // InternalDSLPolicies.g:6742:8: lv_arguments_10_0= ruleJvmArgumentTypeReference
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              								newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_4_2_1_0());
                    	              							
                    	            }
                    	            pushFollow(FOLLOW_43);
                    	            lv_arguments_10_0=ruleJvmArgumentTypeReference();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              								if (current==null) {
                    	              									current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
                    	              								}
                    	              								add(
                    	              									current,
                    	              									"arguments",
                    	              									lv_arguments_10_0,
                    	              									"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
                    	              								afterParserOrEnumRuleCall();
                    	              							
                    	            }

                    	            }


                    	            }

                    	            // InternalDSLPolicies.g:6759:6: (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )*
                    	            loop114:
                    	            do {
                    	                int alt114=2;
                    	                int LA114_0 = input.LA(1);

                    	                if ( (LA114_0==70) ) {
                    	                    alt114=1;
                    	                }


                    	                switch (alt114) {
                    	            	case 1 :
                    	            	    // InternalDSLPolicies.g:6760:7: otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) )
                    	            	    {
                    	            	    otherlv_11=(Token)match(input,70,FOLLOW_42); if (state.failed) return current;
                    	            	    if ( state.backtracking==0 ) {

                    	            	      							newLeafNode(otherlv_11, grammarAccess.getJvmParameterizedTypeReferenceAccess().getCommaKeyword_1_4_2_2_0());
                    	            	      						
                    	            	    }
                    	            	    // InternalDSLPolicies.g:6764:7: ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) )
                    	            	    // InternalDSLPolicies.g:6765:8: (lv_arguments_12_0= ruleJvmArgumentTypeReference )
                    	            	    {
                    	            	    // InternalDSLPolicies.g:6765:8: (lv_arguments_12_0= ruleJvmArgumentTypeReference )
                    	            	    // InternalDSLPolicies.g:6766:9: lv_arguments_12_0= ruleJvmArgumentTypeReference
                    	            	    {
                    	            	    if ( state.backtracking==0 ) {

                    	            	      									newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_4_2_2_1_0());
                    	            	      								
                    	            	    }
                    	            	    pushFollow(FOLLOW_43);
                    	            	    lv_arguments_12_0=ruleJvmArgumentTypeReference();

                    	            	    state._fsp--;
                    	            	    if (state.failed) return current;
                    	            	    if ( state.backtracking==0 ) {

                    	            	      									if (current==null) {
                    	            	      										current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
                    	            	      									}
                    	            	      									add(
                    	            	      										current,
                    	            	      										"arguments",
                    	            	      										lv_arguments_12_0,
                    	            	      										"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
                    	            	      									afterParserOrEnumRuleCall();
                    	            	      								
                    	            	    }

                    	            	    }


                    	            	    }


                    	            	    }
                    	            	    break;

                    	            	default :
                    	            	    break loop114;
                    	                }
                    	            } while (true);

                    	            otherlv_13=(Token)match(input,42,FOLLOW_83); if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              						newLeafNode(otherlv_13, grammarAccess.getJvmParameterizedTypeReferenceAccess().getGreaterThanSignKeyword_1_4_2_3());
                    	              					
                    	            }

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop116;
                        }
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJvmParameterizedTypeReference"


    // $ANTLR start "entryRuleJvmArgumentTypeReference"
    // InternalDSLPolicies.g:6795:1: entryRuleJvmArgumentTypeReference returns [EObject current=null] : iv_ruleJvmArgumentTypeReference= ruleJvmArgumentTypeReference EOF ;
    public final EObject entryRuleJvmArgumentTypeReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmArgumentTypeReference = null;


        try {
            // InternalDSLPolicies.g:6795:65: (iv_ruleJvmArgumentTypeReference= ruleJvmArgumentTypeReference EOF )
            // InternalDSLPolicies.g:6796:2: iv_ruleJvmArgumentTypeReference= ruleJvmArgumentTypeReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmArgumentTypeReferenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJvmArgumentTypeReference=ruleJvmArgumentTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmArgumentTypeReference; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJvmArgumentTypeReference"


    // $ANTLR start "ruleJvmArgumentTypeReference"
    // InternalDSLPolicies.g:6802:1: ruleJvmArgumentTypeReference returns [EObject current=null] : (this_JvmTypeReference_0= ruleJvmTypeReference | this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference ) ;
    public final EObject ruleJvmArgumentTypeReference() throws RecognitionException {
        EObject current = null;

        EObject this_JvmTypeReference_0 = null;

        EObject this_JvmWildcardTypeReference_1 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6808:2: ( (this_JvmTypeReference_0= ruleJvmTypeReference | this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference ) )
            // InternalDSLPolicies.g:6809:2: (this_JvmTypeReference_0= ruleJvmTypeReference | this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference )
            {
            // InternalDSLPolicies.g:6809:2: (this_JvmTypeReference_0= ruleJvmTypeReference | this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference )
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==RULE_ID||LA118_0==23||LA118_0==54) ) {
                alt118=1;
            }
            else if ( (LA118_0==104) ) {
                alt118=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 118, 0, input);

                throw nvae;
            }
            switch (alt118) {
                case 1 :
                    // InternalDSLPolicies.g:6810:3: this_JvmTypeReference_0= ruleJvmTypeReference
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getJvmArgumentTypeReferenceAccess().getJvmTypeReferenceParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_JvmTypeReference_0=ruleJvmTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_JvmTypeReference_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:6819:3: this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getJvmArgumentTypeReferenceAccess().getJvmWildcardTypeReferenceParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_JvmWildcardTypeReference_1=ruleJvmWildcardTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_JvmWildcardTypeReference_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJvmArgumentTypeReference"


    // $ANTLR start "entryRuleJvmWildcardTypeReference"
    // InternalDSLPolicies.g:6831:1: entryRuleJvmWildcardTypeReference returns [EObject current=null] : iv_ruleJvmWildcardTypeReference= ruleJvmWildcardTypeReference EOF ;
    public final EObject entryRuleJvmWildcardTypeReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmWildcardTypeReference = null;


        try {
            // InternalDSLPolicies.g:6831:65: (iv_ruleJvmWildcardTypeReference= ruleJvmWildcardTypeReference EOF )
            // InternalDSLPolicies.g:6832:2: iv_ruleJvmWildcardTypeReference= ruleJvmWildcardTypeReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJvmWildcardTypeReference=ruleJvmWildcardTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmWildcardTypeReference; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJvmWildcardTypeReference"


    // $ANTLR start "ruleJvmWildcardTypeReference"
    // InternalDSLPolicies.g:6838:1: ruleJvmWildcardTypeReference returns [EObject current=null] : ( () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )? ) ;
    public final EObject ruleJvmWildcardTypeReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_constraints_2_0 = null;

        EObject lv_constraints_3_0 = null;

        EObject lv_constraints_4_0 = null;

        EObject lv_constraints_5_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6844:2: ( ( () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )? ) )
            // InternalDSLPolicies.g:6845:2: ( () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )? )
            {
            // InternalDSLPolicies.g:6845:2: ( () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )? )
            // InternalDSLPolicies.g:6846:3: () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )?
            {
            // InternalDSLPolicies.g:6846:3: ()
            // InternalDSLPolicies.g:6847:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getJvmWildcardTypeReferenceAccess().getJvmWildcardTypeReferenceAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,104,FOLLOW_89); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getJvmWildcardTypeReferenceAccess().getQuestionMarkKeyword_1());
              		
            }
            // InternalDSLPolicies.g:6857:3: ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )?
            int alt121=3;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==88) ) {
                alt121=1;
            }
            else if ( (LA121_0==92) ) {
                alt121=2;
            }
            switch (alt121) {
                case 1 :
                    // InternalDSLPolicies.g:6858:4: ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* )
                    {
                    // InternalDSLPolicies.g:6858:4: ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* )
                    // InternalDSLPolicies.g:6859:5: ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )*
                    {
                    // InternalDSLPolicies.g:6859:5: ( (lv_constraints_2_0= ruleJvmUpperBound ) )
                    // InternalDSLPolicies.g:6860:6: (lv_constraints_2_0= ruleJvmUpperBound )
                    {
                    // InternalDSLPolicies.g:6860:6: (lv_constraints_2_0= ruleJvmUpperBound )
                    // InternalDSLPolicies.g:6861:7: lv_constraints_2_0= ruleJvmUpperBound
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmUpperBoundParserRuleCall_2_0_0_0());
                      						
                    }
                    pushFollow(FOLLOW_90);
                    lv_constraints_2_0=ruleJvmUpperBound();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
                      							}
                      							add(
                      								current,
                      								"constraints",
                      								lv_constraints_2_0,
                      								"org.eclipse.xtext.xbase.Xtype.JvmUpperBound");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    // InternalDSLPolicies.g:6878:5: ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )*
                    loop119:
                    do {
                        int alt119=2;
                        int LA119_0 = input.LA(1);

                        if ( (LA119_0==105) ) {
                            alt119=1;
                        }


                        switch (alt119) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:6879:6: (lv_constraints_3_0= ruleJvmUpperBoundAnded )
                    	    {
                    	    // InternalDSLPolicies.g:6879:6: (lv_constraints_3_0= ruleJvmUpperBoundAnded )
                    	    // InternalDSLPolicies.g:6880:7: lv_constraints_3_0= ruleJvmUpperBoundAnded
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmUpperBoundAndedParserRuleCall_2_0_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_90);
                    	    lv_constraints_3_0=ruleJvmUpperBoundAnded();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"constraints",
                    	      								lv_constraints_3_0,
                    	      								"org.eclipse.xtext.xbase.Xtype.JvmUpperBoundAnded");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop119;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:6899:4: ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* )
                    {
                    // InternalDSLPolicies.g:6899:4: ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* )
                    // InternalDSLPolicies.g:6900:5: ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )*
                    {
                    // InternalDSLPolicies.g:6900:5: ( (lv_constraints_4_0= ruleJvmLowerBound ) )
                    // InternalDSLPolicies.g:6901:6: (lv_constraints_4_0= ruleJvmLowerBound )
                    {
                    // InternalDSLPolicies.g:6901:6: (lv_constraints_4_0= ruleJvmLowerBound )
                    // InternalDSLPolicies.g:6902:7: lv_constraints_4_0= ruleJvmLowerBound
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmLowerBoundParserRuleCall_2_1_0_0());
                      						
                    }
                    pushFollow(FOLLOW_90);
                    lv_constraints_4_0=ruleJvmLowerBound();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
                      							}
                      							add(
                      								current,
                      								"constraints",
                      								lv_constraints_4_0,
                      								"org.eclipse.xtext.xbase.Xtype.JvmLowerBound");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    // InternalDSLPolicies.g:6919:5: ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )*
                    loop120:
                    do {
                        int alt120=2;
                        int LA120_0 = input.LA(1);

                        if ( (LA120_0==105) ) {
                            alt120=1;
                        }


                        switch (alt120) {
                    	case 1 :
                    	    // InternalDSLPolicies.g:6920:6: (lv_constraints_5_0= ruleJvmLowerBoundAnded )
                    	    {
                    	    // InternalDSLPolicies.g:6920:6: (lv_constraints_5_0= ruleJvmLowerBoundAnded )
                    	    // InternalDSLPolicies.g:6921:7: lv_constraints_5_0= ruleJvmLowerBoundAnded
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmLowerBoundAndedParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_90);
                    	    lv_constraints_5_0=ruleJvmLowerBoundAnded();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"constraints",
                    	      								lv_constraints_5_0,
                    	      								"org.eclipse.xtext.xbase.Xtype.JvmLowerBoundAnded");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop120;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJvmWildcardTypeReference"


    // $ANTLR start "entryRuleJvmUpperBound"
    // InternalDSLPolicies.g:6944:1: entryRuleJvmUpperBound returns [EObject current=null] : iv_ruleJvmUpperBound= ruleJvmUpperBound EOF ;
    public final EObject entryRuleJvmUpperBound() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmUpperBound = null;


        try {
            // InternalDSLPolicies.g:6944:54: (iv_ruleJvmUpperBound= ruleJvmUpperBound EOF )
            // InternalDSLPolicies.g:6945:2: iv_ruleJvmUpperBound= ruleJvmUpperBound EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmUpperBoundRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJvmUpperBound=ruleJvmUpperBound();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmUpperBound; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJvmUpperBound"


    // $ANTLR start "ruleJvmUpperBound"
    // InternalDSLPolicies.g:6951:1: ruleJvmUpperBound returns [EObject current=null] : (otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleJvmUpperBound() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_typeReference_1_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6957:2: ( (otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) )
            // InternalDSLPolicies.g:6958:2: (otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            {
            // InternalDSLPolicies.g:6958:2: (otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            // InternalDSLPolicies.g:6959:3: otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            {
            otherlv_0=(Token)match(input,88,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getJvmUpperBoundAccess().getExtendsKeyword_0());
              		
            }
            // InternalDSLPolicies.g:6963:3: ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            // InternalDSLPolicies.g:6964:4: (lv_typeReference_1_0= ruleJvmTypeReference )
            {
            // InternalDSLPolicies.g:6964:4: (lv_typeReference_1_0= ruleJvmTypeReference )
            // InternalDSLPolicies.g:6965:5: lv_typeReference_1_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getJvmUpperBoundAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_typeReference_1_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getJvmUpperBoundRule());
              					}
              					set(
              						current,
              						"typeReference",
              						lv_typeReference_1_0,
              						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJvmUpperBound"


    // $ANTLR start "entryRuleJvmUpperBoundAnded"
    // InternalDSLPolicies.g:6986:1: entryRuleJvmUpperBoundAnded returns [EObject current=null] : iv_ruleJvmUpperBoundAnded= ruleJvmUpperBoundAnded EOF ;
    public final EObject entryRuleJvmUpperBoundAnded() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmUpperBoundAnded = null;


        try {
            // InternalDSLPolicies.g:6986:59: (iv_ruleJvmUpperBoundAnded= ruleJvmUpperBoundAnded EOF )
            // InternalDSLPolicies.g:6987:2: iv_ruleJvmUpperBoundAnded= ruleJvmUpperBoundAnded EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmUpperBoundAndedRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJvmUpperBoundAnded=ruleJvmUpperBoundAnded();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmUpperBoundAnded; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJvmUpperBoundAnded"


    // $ANTLR start "ruleJvmUpperBoundAnded"
    // InternalDSLPolicies.g:6993:1: ruleJvmUpperBoundAnded returns [EObject current=null] : (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleJvmUpperBoundAnded() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_typeReference_1_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:6999:2: ( (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) )
            // InternalDSLPolicies.g:7000:2: (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            {
            // InternalDSLPolicies.g:7000:2: (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            // InternalDSLPolicies.g:7001:3: otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            {
            otherlv_0=(Token)match(input,105,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getJvmUpperBoundAndedAccess().getAmpersandKeyword_0());
              		
            }
            // InternalDSLPolicies.g:7005:3: ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            // InternalDSLPolicies.g:7006:4: (lv_typeReference_1_0= ruleJvmTypeReference )
            {
            // InternalDSLPolicies.g:7006:4: (lv_typeReference_1_0= ruleJvmTypeReference )
            // InternalDSLPolicies.g:7007:5: lv_typeReference_1_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getJvmUpperBoundAndedAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_typeReference_1_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getJvmUpperBoundAndedRule());
              					}
              					set(
              						current,
              						"typeReference",
              						lv_typeReference_1_0,
              						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJvmUpperBoundAnded"


    // $ANTLR start "entryRuleJvmLowerBound"
    // InternalDSLPolicies.g:7028:1: entryRuleJvmLowerBound returns [EObject current=null] : iv_ruleJvmLowerBound= ruleJvmLowerBound EOF ;
    public final EObject entryRuleJvmLowerBound() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmLowerBound = null;


        try {
            // InternalDSLPolicies.g:7028:54: (iv_ruleJvmLowerBound= ruleJvmLowerBound EOF )
            // InternalDSLPolicies.g:7029:2: iv_ruleJvmLowerBound= ruleJvmLowerBound EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmLowerBoundRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJvmLowerBound=ruleJvmLowerBound();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmLowerBound; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJvmLowerBound"


    // $ANTLR start "ruleJvmLowerBound"
    // InternalDSLPolicies.g:7035:1: ruleJvmLowerBound returns [EObject current=null] : (otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleJvmLowerBound() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_typeReference_1_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:7041:2: ( (otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) )
            // InternalDSLPolicies.g:7042:2: (otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            {
            // InternalDSLPolicies.g:7042:2: (otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            // InternalDSLPolicies.g:7043:3: otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            {
            otherlv_0=(Token)match(input,92,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getJvmLowerBoundAccess().getSuperKeyword_0());
              		
            }
            // InternalDSLPolicies.g:7047:3: ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            // InternalDSLPolicies.g:7048:4: (lv_typeReference_1_0= ruleJvmTypeReference )
            {
            // InternalDSLPolicies.g:7048:4: (lv_typeReference_1_0= ruleJvmTypeReference )
            // InternalDSLPolicies.g:7049:5: lv_typeReference_1_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getJvmLowerBoundAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_typeReference_1_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getJvmLowerBoundRule());
              					}
              					set(
              						current,
              						"typeReference",
              						lv_typeReference_1_0,
              						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJvmLowerBound"


    // $ANTLR start "entryRuleJvmLowerBoundAnded"
    // InternalDSLPolicies.g:7070:1: entryRuleJvmLowerBoundAnded returns [EObject current=null] : iv_ruleJvmLowerBoundAnded= ruleJvmLowerBoundAnded EOF ;
    public final EObject entryRuleJvmLowerBoundAnded() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmLowerBoundAnded = null;


        try {
            // InternalDSLPolicies.g:7070:59: (iv_ruleJvmLowerBoundAnded= ruleJvmLowerBoundAnded EOF )
            // InternalDSLPolicies.g:7071:2: iv_ruleJvmLowerBoundAnded= ruleJvmLowerBoundAnded EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmLowerBoundAndedRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleJvmLowerBoundAnded=ruleJvmLowerBoundAnded();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmLowerBoundAnded; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJvmLowerBoundAnded"


    // $ANTLR start "ruleJvmLowerBoundAnded"
    // InternalDSLPolicies.g:7077:1: ruleJvmLowerBoundAnded returns [EObject current=null] : (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleJvmLowerBoundAnded() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_typeReference_1_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:7083:2: ( (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) )
            // InternalDSLPolicies.g:7084:2: (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            {
            // InternalDSLPolicies.g:7084:2: (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            // InternalDSLPolicies.g:7085:3: otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            {
            otherlv_0=(Token)match(input,105,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getJvmLowerBoundAndedAccess().getAmpersandKeyword_0());
              		
            }
            // InternalDSLPolicies.g:7089:3: ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            // InternalDSLPolicies.g:7090:4: (lv_typeReference_1_0= ruleJvmTypeReference )
            {
            // InternalDSLPolicies.g:7090:4: (lv_typeReference_1_0= ruleJvmTypeReference )
            // InternalDSLPolicies.g:7091:5: lv_typeReference_1_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getJvmLowerBoundAndedAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_typeReference_1_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getJvmLowerBoundAndedRule());
              					}
              					set(
              						current,
              						"typeReference",
              						lv_typeReference_1_0,
              						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJvmLowerBoundAnded"


    // $ANTLR start "entryRuleQualifiedNameWithWildcard"
    // InternalDSLPolicies.g:7112:1: entryRuleQualifiedNameWithWildcard returns [String current=null] : iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF ;
    public final String entryRuleQualifiedNameWithWildcard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildcard = null;


        try {
            // InternalDSLPolicies.g:7112:65: (iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF )
            // InternalDSLPolicies.g:7113:2: iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedNameWithWildcardRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedNameWithWildcard=ruleQualifiedNameWithWildcard();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifiedNameWithWildcard.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedNameWithWildcard"


    // $ANTLR start "ruleQualifiedNameWithWildcard"
    // InternalDSLPolicies.g:7119:1: ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*' ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildcard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:7125:2: ( (this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*' ) )
            // InternalDSLPolicies.g:7126:2: (this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*' )
            {
            // InternalDSLPolicies.g:7126:2: (this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*' )
            // InternalDSLPolicies.g:7127:3: this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*'
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_91);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_QualifiedName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,67,FOLLOW_92); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopKeyword_1());
              		
            }
            kw=(Token)match(input,59,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getAsteriskKeyword_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedNameWithWildcard"


    // $ANTLR start "entryRuleValidID"
    // InternalDSLPolicies.g:7151:1: entryRuleValidID returns [String current=null] : iv_ruleValidID= ruleValidID EOF ;
    public final String entryRuleValidID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleValidID = null;


        try {
            // InternalDSLPolicies.g:7151:47: (iv_ruleValidID= ruleValidID EOF )
            // InternalDSLPolicies.g:7152:2: iv_ruleValidID= ruleValidID EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValidIDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleValidID=ruleValidID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValidID.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValidID"


    // $ANTLR start "ruleValidID"
    // InternalDSLPolicies.g:7158:1: ruleValidID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleValidID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;


        	enterRule();

        try {
            // InternalDSLPolicies.g:7164:2: (this_ID_0= RULE_ID )
            // InternalDSLPolicies.g:7165:2: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_ID_0);
              	
            }
            if ( state.backtracking==0 ) {

              		newLeafNode(this_ID_0, grammarAccess.getValidIDAccess().getIDTerminalRuleCall());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValidID"


    // $ANTLR start "entryRuleXImportDeclaration"
    // InternalDSLPolicies.g:7175:1: entryRuleXImportDeclaration returns [EObject current=null] : iv_ruleXImportDeclaration= ruleXImportDeclaration EOF ;
    public final EObject entryRuleXImportDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXImportDeclaration = null;


        try {
            // InternalDSLPolicies.g:7175:59: (iv_ruleXImportDeclaration= ruleXImportDeclaration EOF )
            // InternalDSLPolicies.g:7176:2: iv_ruleXImportDeclaration= ruleXImportDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXImportDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleXImportDeclaration=ruleXImportDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXImportDeclaration; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleXImportDeclaration"


    // $ANTLR start "ruleXImportDeclaration"
    // InternalDSLPolicies.g:7182:1: ruleXImportDeclaration returns [EObject current=null] : (otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )? ) ;
    public final EObject ruleXImportDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_static_1_0=null;
        Token lv_extension_2_0=null;
        Token lv_wildcard_4_0=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_memberName_5_0 = null;

        AntlrDatatypeRuleToken lv_importedNamespace_7_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:7188:2: ( (otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )? ) )
            // InternalDSLPolicies.g:7189:2: (otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )? )
            {
            // InternalDSLPolicies.g:7189:2: (otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )? )
            // InternalDSLPolicies.g:7190:3: otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )?
            {
            otherlv_0=(Token)match(input,90,FOLLOW_93); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getXImportDeclarationAccess().getImportKeyword_0());
              		
            }
            // InternalDSLPolicies.g:7194:3: ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) )
            int alt124=3;
            alt124 = dfa124.predict(input);
            switch (alt124) {
                case 1 :
                    // InternalDSLPolicies.g:7195:4: ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) )
                    {
                    // InternalDSLPolicies.g:7195:4: ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) )
                    // InternalDSLPolicies.g:7196:5: ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) )
                    {
                    // InternalDSLPolicies.g:7196:5: ( (lv_static_1_0= 'static' ) )
                    // InternalDSLPolicies.g:7197:6: (lv_static_1_0= 'static' )
                    {
                    // InternalDSLPolicies.g:7197:6: (lv_static_1_0= 'static' )
                    // InternalDSLPolicies.g:7198:7: lv_static_1_0= 'static'
                    {
                    lv_static_1_0=(Token)match(input,89,FOLLOW_94); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_static_1_0, grammarAccess.getXImportDeclarationAccess().getStaticStaticKeyword_1_0_0_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getXImportDeclarationRule());
                      							}
                      							setWithLastConsumed(current, "static", true, "static");
                      						
                    }

                    }


                    }

                    // InternalDSLPolicies.g:7210:5: ( (lv_extension_2_0= 'extension' ) )?
                    int alt122=2;
                    int LA122_0 = input.LA(1);

                    if ( (LA122_0==91) ) {
                        alt122=1;
                    }
                    switch (alt122) {
                        case 1 :
                            // InternalDSLPolicies.g:7211:6: (lv_extension_2_0= 'extension' )
                            {
                            // InternalDSLPolicies.g:7211:6: (lv_extension_2_0= 'extension' )
                            // InternalDSLPolicies.g:7212:7: lv_extension_2_0= 'extension'
                            {
                            lv_extension_2_0=(Token)match(input,91,FOLLOW_94); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_extension_2_0, grammarAccess.getXImportDeclarationAccess().getExtensionExtensionKeyword_1_0_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getXImportDeclarationRule());
                              							}
                              							setWithLastConsumed(current, "extension", true, "extension");
                              						
                            }

                            }


                            }
                            break;

                    }

                    // InternalDSLPolicies.g:7224:5: ( ( ruleQualifiedNameInStaticImport ) )
                    // InternalDSLPolicies.g:7225:6: ( ruleQualifiedNameInStaticImport )
                    {
                    // InternalDSLPolicies.g:7225:6: ( ruleQualifiedNameInStaticImport )
                    // InternalDSLPolicies.g:7226:7: ruleQualifiedNameInStaticImport
                    {
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getXImportDeclarationRule());
                      							}
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getXImportDeclarationAccess().getImportedTypeJvmDeclaredTypeCrossReference_1_0_2_0());
                      						
                    }
                    pushFollow(FOLLOW_95);
                    ruleQualifiedNameInStaticImport();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    // InternalDSLPolicies.g:7240:5: ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) )
                    int alt123=2;
                    int LA123_0 = input.LA(1);

                    if ( (LA123_0==59) ) {
                        alt123=1;
                    }
                    else if ( (LA123_0==RULE_ID) ) {
                        alt123=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 123, 0, input);

                        throw nvae;
                    }
                    switch (alt123) {
                        case 1 :
                            // InternalDSLPolicies.g:7241:6: ( (lv_wildcard_4_0= '*' ) )
                            {
                            // InternalDSLPolicies.g:7241:6: ( (lv_wildcard_4_0= '*' ) )
                            // InternalDSLPolicies.g:7242:7: (lv_wildcard_4_0= '*' )
                            {
                            // InternalDSLPolicies.g:7242:7: (lv_wildcard_4_0= '*' )
                            // InternalDSLPolicies.g:7243:8: lv_wildcard_4_0= '*'
                            {
                            lv_wildcard_4_0=(Token)match(input,59,FOLLOW_96); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_wildcard_4_0, grammarAccess.getXImportDeclarationAccess().getWildcardAsteriskKeyword_1_0_3_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getXImportDeclarationRule());
                              								}
                              								setWithLastConsumed(current, "wildcard", true, "*");
                              							
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalDSLPolicies.g:7256:6: ( (lv_memberName_5_0= ruleValidID ) )
                            {
                            // InternalDSLPolicies.g:7256:6: ( (lv_memberName_5_0= ruleValidID ) )
                            // InternalDSLPolicies.g:7257:7: (lv_memberName_5_0= ruleValidID )
                            {
                            // InternalDSLPolicies.g:7257:7: (lv_memberName_5_0= ruleValidID )
                            // InternalDSLPolicies.g:7258:8: lv_memberName_5_0= ruleValidID
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getXImportDeclarationAccess().getMemberNameValidIDParserRuleCall_1_0_3_1_0());
                              							
                            }
                            pushFollow(FOLLOW_96);
                            lv_memberName_5_0=ruleValidID();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getXImportDeclarationRule());
                              								}
                              								set(
                              									current,
                              									"memberName",
                              									lv_memberName_5_0,
                              									"org.eclipse.xtext.xbase.Xtype.ValidID");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalDSLPolicies.g:7278:4: ( ( ruleQualifiedName ) )
                    {
                    // InternalDSLPolicies.g:7278:4: ( ( ruleQualifiedName ) )
                    // InternalDSLPolicies.g:7279:5: ( ruleQualifiedName )
                    {
                    // InternalDSLPolicies.g:7279:5: ( ruleQualifiedName )
                    // InternalDSLPolicies.g:7280:6: ruleQualifiedName
                    {
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getXImportDeclarationRule());
                      						}
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXImportDeclarationAccess().getImportedTypeJvmDeclaredTypeCrossReference_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_96);
                    ruleQualifiedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalDSLPolicies.g:7295:4: ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) )
                    {
                    // InternalDSLPolicies.g:7295:4: ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) )
                    // InternalDSLPolicies.g:7296:5: (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard )
                    {
                    // InternalDSLPolicies.g:7296:5: (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard )
                    // InternalDSLPolicies.g:7297:6: lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getXImportDeclarationAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_96);
                    lv_importedNamespace_7_0=ruleQualifiedNameWithWildcard();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getXImportDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"importedNamespace",
                      							lv_importedNamespace_7_0,
                      							"org.eclipse.xtext.xbase.Xtype.QualifiedNameWithWildcard");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalDSLPolicies.g:7315:3: (otherlv_8= ';' )?
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==22) ) {
                alt125=1;
            }
            switch (alt125) {
                case 1 :
                    // InternalDSLPolicies.g:7316:4: otherlv_8= ';'
                    {
                    otherlv_8=(Token)match(input,22,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getXImportDeclarationAccess().getSemicolonKeyword_2());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleXImportDeclaration"


    // $ANTLR start "entryRuleQualifiedNameInStaticImport"
    // InternalDSLPolicies.g:7325:1: entryRuleQualifiedNameInStaticImport returns [String current=null] : iv_ruleQualifiedNameInStaticImport= ruleQualifiedNameInStaticImport EOF ;
    public final String entryRuleQualifiedNameInStaticImport() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameInStaticImport = null;


        try {
            // InternalDSLPolicies.g:7325:67: (iv_ruleQualifiedNameInStaticImport= ruleQualifiedNameInStaticImport EOF )
            // InternalDSLPolicies.g:7326:2: iv_ruleQualifiedNameInStaticImport= ruleQualifiedNameInStaticImport EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedNameInStaticImportRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedNameInStaticImport=ruleQualifiedNameInStaticImport();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifiedNameInStaticImport.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedNameInStaticImport"


    // $ANTLR start "ruleQualifiedNameInStaticImport"
    // InternalDSLPolicies.g:7332:1: ruleQualifiedNameInStaticImport returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ValidID_0= ruleValidID kw= '.' )+ ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameInStaticImport() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ValidID_0 = null;



        	enterRule();

        try {
            // InternalDSLPolicies.g:7338:2: ( (this_ValidID_0= ruleValidID kw= '.' )+ )
            // InternalDSLPolicies.g:7339:2: (this_ValidID_0= ruleValidID kw= '.' )+
            {
            // InternalDSLPolicies.g:7339:2: (this_ValidID_0= ruleValidID kw= '.' )+
            int cnt126=0;
            loop126:
            do {
                int alt126=2;
                int LA126_0 = input.LA(1);

                if ( (LA126_0==RULE_ID) ) {
                    int LA126_2 = input.LA(2);

                    if ( (LA126_2==67) ) {
                        alt126=1;
                    }


                }


                switch (alt126) {
            	case 1 :
            	    // InternalDSLPolicies.g:7340:3: this_ValidID_0= ruleValidID kw= '.'
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getQualifiedNameInStaticImportAccess().getValidIDParserRuleCall_0());
            	      		
            	    }
            	    pushFollow(FOLLOW_91);
            	    this_ValidID_0=ruleValidID();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_ValidID_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }
            	    kw=(Token)match(input,67,FOLLOW_97); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(kw);
            	      			newLeafNode(kw, grammarAccess.getQualifiedNameInStaticImportAccess().getFullStopKeyword_1());
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt126 >= 1 ) break loop126;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(126, input);
                        throw eee;
                }
                cnt126++;
            } while (true);


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedNameInStaticImport"

    // $ANTLR start synpred1_InternalDSLPolicies
    public final void synpred1_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:1180:6: ( ( () ( ( ruleOpMultiAssign ) ) ) )
        // InternalDSLPolicies.g:1180:7: ( () ( ( ruleOpMultiAssign ) ) )
        {
        // InternalDSLPolicies.g:1180:7: ( () ( ( ruleOpMultiAssign ) ) )
        // InternalDSLPolicies.g:1181:7: () ( ( ruleOpMultiAssign ) )
        {
        // InternalDSLPolicies.g:1181:7: ()
        // InternalDSLPolicies.g:1182:7: 
        {
        }

        // InternalDSLPolicies.g:1183:7: ( ( ruleOpMultiAssign ) )
        // InternalDSLPolicies.g:1184:8: ( ruleOpMultiAssign )
        {
        // InternalDSLPolicies.g:1184:8: ( ruleOpMultiAssign )
        // InternalDSLPolicies.g:1185:9: ruleOpMultiAssign
        {
        pushFollow(FOLLOW_2);
        ruleOpMultiAssign();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred1_InternalDSLPolicies

    // $ANTLR start synpred2_InternalDSLPolicies
    public final void synpred2_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:1374:5: ( ( () ( ( ruleOpOr ) ) ) )
        // InternalDSLPolicies.g:1374:6: ( () ( ( ruleOpOr ) ) )
        {
        // InternalDSLPolicies.g:1374:6: ( () ( ( ruleOpOr ) ) )
        // InternalDSLPolicies.g:1375:6: () ( ( ruleOpOr ) )
        {
        // InternalDSLPolicies.g:1375:6: ()
        // InternalDSLPolicies.g:1376:6: 
        {
        }

        // InternalDSLPolicies.g:1377:6: ( ( ruleOpOr ) )
        // InternalDSLPolicies.g:1378:7: ( ruleOpOr )
        {
        // InternalDSLPolicies.g:1378:7: ( ruleOpOr )
        // InternalDSLPolicies.g:1379:8: ruleOpOr
        {
        pushFollow(FOLLOW_2);
        ruleOpOr();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred2_InternalDSLPolicies

    // $ANTLR start synpred3_InternalDSLPolicies
    public final void synpred3_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:1481:5: ( ( () ( ( ruleOpAnd ) ) ) )
        // InternalDSLPolicies.g:1481:6: ( () ( ( ruleOpAnd ) ) )
        {
        // InternalDSLPolicies.g:1481:6: ( () ( ( ruleOpAnd ) ) )
        // InternalDSLPolicies.g:1482:6: () ( ( ruleOpAnd ) )
        {
        // InternalDSLPolicies.g:1482:6: ()
        // InternalDSLPolicies.g:1483:6: 
        {
        }

        // InternalDSLPolicies.g:1484:6: ( ( ruleOpAnd ) )
        // InternalDSLPolicies.g:1485:7: ( ruleOpAnd )
        {
        // InternalDSLPolicies.g:1485:7: ( ruleOpAnd )
        // InternalDSLPolicies.g:1486:8: ruleOpAnd
        {
        pushFollow(FOLLOW_2);
        ruleOpAnd();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred3_InternalDSLPolicies

    // $ANTLR start synpred4_InternalDSLPolicies
    public final void synpred4_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:1588:5: ( ( () ( ( ruleOpEquality ) ) ) )
        // InternalDSLPolicies.g:1588:6: ( () ( ( ruleOpEquality ) ) )
        {
        // InternalDSLPolicies.g:1588:6: ( () ( ( ruleOpEquality ) ) )
        // InternalDSLPolicies.g:1589:6: () ( ( ruleOpEquality ) )
        {
        // InternalDSLPolicies.g:1589:6: ()
        // InternalDSLPolicies.g:1590:6: 
        {
        }

        // InternalDSLPolicies.g:1591:6: ( ( ruleOpEquality ) )
        // InternalDSLPolicies.g:1592:7: ( ruleOpEquality )
        {
        // InternalDSLPolicies.g:1592:7: ( ruleOpEquality )
        // InternalDSLPolicies.g:1593:8: ruleOpEquality
        {
        pushFollow(FOLLOW_2);
        ruleOpEquality();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred4_InternalDSLPolicies

    // $ANTLR start synpred5_InternalDSLPolicies
    public final void synpred5_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:1716:6: ( ( () 'instanceof' ) )
        // InternalDSLPolicies.g:1716:7: ( () 'instanceof' )
        {
        // InternalDSLPolicies.g:1716:7: ( () 'instanceof' )
        // InternalDSLPolicies.g:1717:7: () 'instanceof'
        {
        // InternalDSLPolicies.g:1717:7: ()
        // InternalDSLPolicies.g:1718:7: 
        {
        }

        match(input,50,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred5_InternalDSLPolicies

    // $ANTLR start synpred6_InternalDSLPolicies
    public final void synpred6_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:1759:6: ( ( () ( ( ruleOpCompare ) ) ) )
        // InternalDSLPolicies.g:1759:7: ( () ( ( ruleOpCompare ) ) )
        {
        // InternalDSLPolicies.g:1759:7: ( () ( ( ruleOpCompare ) ) )
        // InternalDSLPolicies.g:1760:7: () ( ( ruleOpCompare ) )
        {
        // InternalDSLPolicies.g:1760:7: ()
        // InternalDSLPolicies.g:1761:7: 
        {
        }

        // InternalDSLPolicies.g:1762:7: ( ( ruleOpCompare ) )
        // InternalDSLPolicies.g:1763:8: ( ruleOpCompare )
        {
        // InternalDSLPolicies.g:1763:8: ( ruleOpCompare )
        // InternalDSLPolicies.g:1764:9: ruleOpCompare
        {
        pushFollow(FOLLOW_2);
        ruleOpCompare();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred6_InternalDSLPolicies

    // $ANTLR start synpred7_InternalDSLPolicies
    public final void synpred7_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:1894:5: ( ( () ( ( ruleOpOther ) ) ) )
        // InternalDSLPolicies.g:1894:6: ( () ( ( ruleOpOther ) ) )
        {
        // InternalDSLPolicies.g:1894:6: ( () ( ( ruleOpOther ) ) )
        // InternalDSLPolicies.g:1895:6: () ( ( ruleOpOther ) )
        {
        // InternalDSLPolicies.g:1895:6: ()
        // InternalDSLPolicies.g:1896:6: 
        {
        }

        // InternalDSLPolicies.g:1897:6: ( ( ruleOpOther ) )
        // InternalDSLPolicies.g:1898:7: ( ruleOpOther )
        {
        // InternalDSLPolicies.g:1898:7: ( ruleOpOther )
        // InternalDSLPolicies.g:1899:8: ruleOpOther
        {
        pushFollow(FOLLOW_2);
        ruleOpOther();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred7_InternalDSLPolicies

    // $ANTLR start synpred8_InternalDSLPolicies
    public final void synpred8_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2014:6: ( ( '>' '>' ) )
        // InternalDSLPolicies.g:2014:7: ( '>' '>' )
        {
        // InternalDSLPolicies.g:2014:7: ( '>' '>' )
        // InternalDSLPolicies.g:2015:7: '>' '>'
        {
        match(input,42,FOLLOW_33); if (state.failed) return ;
        match(input,42,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred8_InternalDSLPolicies

    // $ANTLR start synpred9_InternalDSLPolicies
    public final void synpred9_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2049:6: ( ( '<' '<' ) )
        // InternalDSLPolicies.g:2049:7: ( '<' '<' )
        {
        // InternalDSLPolicies.g:2049:7: ( '<' '<' )
        // InternalDSLPolicies.g:2050:7: '<' '<'
        {
        match(input,41,FOLLOW_23); if (state.failed) return ;
        match(input,41,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred9_InternalDSLPolicies

    // $ANTLR start synpred10_InternalDSLPolicies
    public final void synpred10_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2122:5: ( ( () ( ( ruleOpAdd ) ) ) )
        // InternalDSLPolicies.g:2122:6: ( () ( ( ruleOpAdd ) ) )
        {
        // InternalDSLPolicies.g:2122:6: ( () ( ( ruleOpAdd ) ) )
        // InternalDSLPolicies.g:2123:6: () ( ( ruleOpAdd ) )
        {
        // InternalDSLPolicies.g:2123:6: ()
        // InternalDSLPolicies.g:2124:6: 
        {
        }

        // InternalDSLPolicies.g:2125:6: ( ( ruleOpAdd ) )
        // InternalDSLPolicies.g:2126:7: ( ruleOpAdd )
        {
        // InternalDSLPolicies.g:2126:7: ( ruleOpAdd )
        // InternalDSLPolicies.g:2127:8: ruleOpAdd
        {
        pushFollow(FOLLOW_2);
        ruleOpAdd();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred10_InternalDSLPolicies

    // $ANTLR start synpred11_InternalDSLPolicies
    public final void synpred11_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2237:5: ( ( () ( ( ruleOpMulti ) ) ) )
        // InternalDSLPolicies.g:2237:6: ( () ( ( ruleOpMulti ) ) )
        {
        // InternalDSLPolicies.g:2237:6: ( () ( ( ruleOpMulti ) ) )
        // InternalDSLPolicies.g:2238:6: () ( ( ruleOpMulti ) )
        {
        // InternalDSLPolicies.g:2238:6: ()
        // InternalDSLPolicies.g:2239:6: 
        {
        }

        // InternalDSLPolicies.g:2240:6: ( ( ruleOpMulti ) )
        // InternalDSLPolicies.g:2241:7: ( ruleOpMulti )
        {
        // InternalDSLPolicies.g:2241:7: ( ruleOpMulti )
        // InternalDSLPolicies.g:2242:8: ruleOpMulti
        {
        pushFollow(FOLLOW_2);
        ruleOpMulti();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred11_InternalDSLPolicies

    // $ANTLR start synpred12_InternalDSLPolicies
    public final void synpred12_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2472:5: ( ( () 'as' ) )
        // InternalDSLPolicies.g:2472:6: ( () 'as' )
        {
        // InternalDSLPolicies.g:2472:6: ( () 'as' )
        // InternalDSLPolicies.g:2473:6: () 'as'
        {
        // InternalDSLPolicies.g:2473:6: ()
        // InternalDSLPolicies.g:2474:6: 
        {
        }

        match(input,64,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred12_InternalDSLPolicies

    // $ANTLR start synpred13_InternalDSLPolicies
    public final void synpred13_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2540:4: ( ( () ( ( ruleOpPostfix ) ) ) )
        // InternalDSLPolicies.g:2540:5: ( () ( ( ruleOpPostfix ) ) )
        {
        // InternalDSLPolicies.g:2540:5: ( () ( ( ruleOpPostfix ) ) )
        // InternalDSLPolicies.g:2541:5: () ( ( ruleOpPostfix ) )
        {
        // InternalDSLPolicies.g:2541:5: ()
        // InternalDSLPolicies.g:2542:5: 
        {
        }

        // InternalDSLPolicies.g:2543:5: ( ( ruleOpPostfix ) )
        // InternalDSLPolicies.g:2544:6: ( ruleOpPostfix )
        {
        // InternalDSLPolicies.g:2544:6: ( ruleOpPostfix )
        // InternalDSLPolicies.g:2545:7: ruleOpPostfix
        {
        pushFollow(FOLLOW_2);
        ruleOpPostfix();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred13_InternalDSLPolicies

    // $ANTLR start synpred14_InternalDSLPolicies
    public final void synpred14_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2636:6: ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )
        // InternalDSLPolicies.g:2636:7: ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign )
        {
        // InternalDSLPolicies.g:2636:7: ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign )
        // InternalDSLPolicies.g:2637:7: () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign
        {
        // InternalDSLPolicies.g:2637:7: ()
        // InternalDSLPolicies.g:2638:7: 
        {
        }

        // InternalDSLPolicies.g:2639:7: ( '.' | ( ( '::' ) ) )
        int alt127=2;
        int LA127_0 = input.LA(1);

        if ( (LA127_0==67) ) {
            alt127=1;
        }
        else if ( (LA127_0==68) ) {
            alt127=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 127, 0, input);

            throw nvae;
        }
        switch (alt127) {
            case 1 :
                // InternalDSLPolicies.g:2640:8: '.'
                {
                match(input,67,FOLLOW_40); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalDSLPolicies.g:2642:8: ( ( '::' ) )
                {
                // InternalDSLPolicies.g:2642:8: ( ( '::' ) )
                // InternalDSLPolicies.g:2643:9: ( '::' )
                {
                // InternalDSLPolicies.g:2643:9: ( '::' )
                // InternalDSLPolicies.g:2644:10: '::'
                {
                match(input,68,FOLLOW_40); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalDSLPolicies.g:2648:7: ( ( ruleFeatureCallID ) )
        // InternalDSLPolicies.g:2649:8: ( ruleFeatureCallID )
        {
        // InternalDSLPolicies.g:2649:8: ( ruleFeatureCallID )
        // InternalDSLPolicies.g:2650:9: ruleFeatureCallID
        {
        pushFollow(FOLLOW_7);
        ruleFeatureCallID();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        pushFollow(FOLLOW_2);
        ruleOpSingleAssign();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred14_InternalDSLPolicies

    // $ANTLR start synpred15_InternalDSLPolicies
    public final void synpred15_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2733:6: ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )
        // InternalDSLPolicies.g:2733:7: ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) )
        {
        // InternalDSLPolicies.g:2733:7: ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) )
        // InternalDSLPolicies.g:2734:7: () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) )
        {
        // InternalDSLPolicies.g:2734:7: ()
        // InternalDSLPolicies.g:2735:7: 
        {
        }

        // InternalDSLPolicies.g:2736:7: ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) )
        int alt128=3;
        switch ( input.LA(1) ) {
        case 67:
            {
            alt128=1;
            }
            break;
        case 69:
            {
            alt128=2;
            }
            break;
        case 68:
            {
            alt128=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 128, 0, input);

            throw nvae;
        }

        switch (alt128) {
            case 1 :
                // InternalDSLPolicies.g:2737:8: '.'
                {
                match(input,67,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalDSLPolicies.g:2739:8: ( ( '?.' ) )
                {
                // InternalDSLPolicies.g:2739:8: ( ( '?.' ) )
                // InternalDSLPolicies.g:2740:9: ( '?.' )
                {
                // InternalDSLPolicies.g:2740:9: ( '?.' )
                // InternalDSLPolicies.g:2741:10: '?.'
                {
                match(input,69,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;
            case 3 :
                // InternalDSLPolicies.g:2745:8: ( ( '::' ) )
                {
                // InternalDSLPolicies.g:2745:8: ( ( '::' ) )
                // InternalDSLPolicies.g:2746:9: ( '::' )
                {
                // InternalDSLPolicies.g:2746:9: ( '::' )
                // InternalDSLPolicies.g:2747:10: '::'
                {
                match(input,68,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred15_InternalDSLPolicies

    // $ANTLR start synpred16_InternalDSLPolicies
    public final void synpred16_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2871:7: ( ( '(' ) )
        // InternalDSLPolicies.g:2871:8: ( '(' )
        {
        // InternalDSLPolicies.g:2871:8: ( '(' )
        // InternalDSLPolicies.g:2872:8: '('
        {
        match(input,23,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred16_InternalDSLPolicies

    // $ANTLR start synpred17_InternalDSLPolicies
    public final void synpred17_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2890:8: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )
        // InternalDSLPolicies.g:2890:9: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        {
        // InternalDSLPolicies.g:2890:9: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        // InternalDSLPolicies.g:2891:9: () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) )
        {
        // InternalDSLPolicies.g:2891:9: ()
        // InternalDSLPolicies.g:2892:9: 
        {
        }

        // InternalDSLPolicies.g:2893:9: ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )?
        int alt130=2;
        int LA130_0 = input.LA(1);

        if ( (LA130_0==RULE_ID||LA130_0==23||LA130_0==54) ) {
            alt130=1;
        }
        switch (alt130) {
            case 1 :
                // InternalDSLPolicies.g:2894:10: ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )*
                {
                // InternalDSLPolicies.g:2894:10: ( ( ruleJvmFormalParameter ) )
                // InternalDSLPolicies.g:2895:11: ( ruleJvmFormalParameter )
                {
                // InternalDSLPolicies.g:2895:11: ( ruleJvmFormalParameter )
                // InternalDSLPolicies.g:2896:12: ruleJvmFormalParameter
                {
                pushFollow(FOLLOW_55);
                ruleJvmFormalParameter();

                state._fsp--;
                if (state.failed) return ;

                }


                }

                // InternalDSLPolicies.g:2899:10: ( ',' ( ( ruleJvmFormalParameter ) ) )*
                loop129:
                do {
                    int alt129=2;
                    int LA129_0 = input.LA(1);

                    if ( (LA129_0==70) ) {
                        alt129=1;
                    }


                    switch (alt129) {
                	case 1 :
                	    // InternalDSLPolicies.g:2900:11: ',' ( ( ruleJvmFormalParameter ) )
                	    {
                	    match(input,70,FOLLOW_30); if (state.failed) return ;
                	    // InternalDSLPolicies.g:2901:11: ( ( ruleJvmFormalParameter ) )
                	    // InternalDSLPolicies.g:2902:12: ( ruleJvmFormalParameter )
                	    {
                	    // InternalDSLPolicies.g:2902:12: ( ruleJvmFormalParameter )
                	    // InternalDSLPolicies.g:2903:13: ruleJvmFormalParameter
                	    {
                	    pushFollow(FOLLOW_55);
                	    ruleJvmFormalParameter();

                	    state._fsp--;
                	    if (state.failed) return ;

                	    }


                	    }


                	    }
                	    break;

                	default :
                	    break loop129;
                    }
                } while (true);


                }
                break;

        }

        // InternalDSLPolicies.g:2908:9: ( ( '|' ) )
        // InternalDSLPolicies.g:2909:10: ( '|' )
        {
        // InternalDSLPolicies.g:2909:10: ( '|' )
        // InternalDSLPolicies.g:2910:11: '|'
        {
        match(input,76,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred17_InternalDSLPolicies

    // $ANTLR start synpred18_InternalDSLPolicies
    public final void synpred18_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:2987:6: ( ( () '[' ) )
        // InternalDSLPolicies.g:2987:7: ( () '[' )
        {
        // InternalDSLPolicies.g:2987:7: ( () '[' )
        // InternalDSLPolicies.g:2988:7: () '['
        {
        // InternalDSLPolicies.g:2988:7: ()
        // InternalDSLPolicies.g:2989:7: 
        {
        }

        match(input,74,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred18_InternalDSLPolicies

    // $ANTLR start synpred19_InternalDSLPolicies
    public final void synpred19_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:3060:4: ( ( () 'synchronized' '(' ) )
        // InternalDSLPolicies.g:3060:5: ( () 'synchronized' '(' )
        {
        // InternalDSLPolicies.g:3060:5: ( () 'synchronized' '(' )
        // InternalDSLPolicies.g:3061:5: () 'synchronized' '('
        {
        // InternalDSLPolicies.g:3061:5: ()
        // InternalDSLPolicies.g:3062:5: 
        {
        }

        match(input,102,FOLLOW_15); if (state.failed) return ;
        match(input,23,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred19_InternalDSLPolicies

    // $ANTLR start synpred20_InternalDSLPolicies
    public final void synpred20_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:3105:4: ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )
        // InternalDSLPolicies.g:3105:5: ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' )
        {
        // InternalDSLPolicies.g:3105:5: ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' )
        // InternalDSLPolicies.g:3106:5: () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':'
        {
        // InternalDSLPolicies.g:3106:5: ()
        // InternalDSLPolicies.g:3107:5: 
        {
        }

        match(input,83,FOLLOW_15); if (state.failed) return ;
        match(input,23,FOLLOW_30); if (state.failed) return ;
        // InternalDSLPolicies.g:3110:5: ( ( ruleJvmFormalParameter ) )
        // InternalDSLPolicies.g:3111:6: ( ruleJvmFormalParameter )
        {
        // InternalDSLPolicies.g:3111:6: ( ruleJvmFormalParameter )
        // InternalDSLPolicies.g:3112:7: ruleJvmFormalParameter
        {
        pushFollow(FOLLOW_62);
        ruleJvmFormalParameter();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        match(input,80,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred20_InternalDSLPolicies

    // $ANTLR start synpred21_InternalDSLPolicies
    public final void synpred21_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:3219:4: ( ( () '[' ) )
        // InternalDSLPolicies.g:3219:5: ( () '[' )
        {
        // InternalDSLPolicies.g:3219:5: ( () '[' )
        // InternalDSLPolicies.g:3220:5: () '['
        {
        // InternalDSLPolicies.g:3220:5: ()
        // InternalDSLPolicies.g:3221:5: 
        {
        }

        match(input,74,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred21_InternalDSLPolicies

    // $ANTLR start synpred23_InternalDSLPolicies
    public final void synpred23_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:3524:4: ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )
        // InternalDSLPolicies.g:3524:5: ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        {
        // InternalDSLPolicies.g:3524:5: ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        // InternalDSLPolicies.g:3525:5: ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) )
        {
        // InternalDSLPolicies.g:3525:5: ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )?
        int alt132=2;
        int LA132_0 = input.LA(1);

        if ( (LA132_0==RULE_ID||LA132_0==23||LA132_0==54) ) {
            alt132=1;
        }
        switch (alt132) {
            case 1 :
                // InternalDSLPolicies.g:3526:6: ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )*
                {
                // InternalDSLPolicies.g:3526:6: ( ( ruleJvmFormalParameter ) )
                // InternalDSLPolicies.g:3527:7: ( ruleJvmFormalParameter )
                {
                // InternalDSLPolicies.g:3527:7: ( ruleJvmFormalParameter )
                // InternalDSLPolicies.g:3528:8: ruleJvmFormalParameter
                {
                pushFollow(FOLLOW_55);
                ruleJvmFormalParameter();

                state._fsp--;
                if (state.failed) return ;

                }


                }

                // InternalDSLPolicies.g:3531:6: ( ',' ( ( ruleJvmFormalParameter ) ) )*
                loop131:
                do {
                    int alt131=2;
                    int LA131_0 = input.LA(1);

                    if ( (LA131_0==70) ) {
                        alt131=1;
                    }


                    switch (alt131) {
                	case 1 :
                	    // InternalDSLPolicies.g:3532:7: ',' ( ( ruleJvmFormalParameter ) )
                	    {
                	    match(input,70,FOLLOW_30); if (state.failed) return ;
                	    // InternalDSLPolicies.g:3533:7: ( ( ruleJvmFormalParameter ) )
                	    // InternalDSLPolicies.g:3534:8: ( ruleJvmFormalParameter )
                	    {
                	    // InternalDSLPolicies.g:3534:8: ( ruleJvmFormalParameter )
                	    // InternalDSLPolicies.g:3535:9: ruleJvmFormalParameter
                	    {
                	    pushFollow(FOLLOW_55);
                	    ruleJvmFormalParameter();

                	    state._fsp--;
                	    if (state.failed) return ;

                	    }


                	    }


                	    }
                	    break;

                	default :
                	    break loop131;
                    }
                } while (true);


                }
                break;

        }

        // InternalDSLPolicies.g:3540:5: ( ( '|' ) )
        // InternalDSLPolicies.g:3541:6: ( '|' )
        {
        // InternalDSLPolicies.g:3541:6: ( '|' )
        // InternalDSLPolicies.g:3542:7: '|'
        {
        match(input,76,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred23_InternalDSLPolicies

    // $ANTLR start synpred25_InternalDSLPolicies
    public final void synpred25_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:3933:5: ( 'else' )
        // InternalDSLPolicies.g:3933:6: 'else'
        {
        match(input,78,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred25_InternalDSLPolicies

    // $ANTLR start synpred26_InternalDSLPolicies
    public final void synpred26_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:3992:6: ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )
        // InternalDSLPolicies.g:3992:7: ( '(' ( ( ruleJvmFormalParameter ) ) ':' )
        {
        // InternalDSLPolicies.g:3992:7: ( '(' ( ( ruleJvmFormalParameter ) ) ':' )
        // InternalDSLPolicies.g:3993:7: '(' ( ( ruleJvmFormalParameter ) ) ':'
        {
        match(input,23,FOLLOW_30); if (state.failed) return ;
        // InternalDSLPolicies.g:3994:7: ( ( ruleJvmFormalParameter ) )
        // InternalDSLPolicies.g:3995:8: ( ruleJvmFormalParameter )
        {
        // InternalDSLPolicies.g:3995:8: ( ruleJvmFormalParameter )
        // InternalDSLPolicies.g:3996:9: ruleJvmFormalParameter
        {
        pushFollow(FOLLOW_62);
        ruleJvmFormalParameter();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        match(input,80,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred26_InternalDSLPolicies

    // $ANTLR start synpred27_InternalDSLPolicies
    public final void synpred27_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:4059:6: ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )
        // InternalDSLPolicies.g:4059:7: ( ( ( ruleJvmFormalParameter ) ) ':' )
        {
        // InternalDSLPolicies.g:4059:7: ( ( ( ruleJvmFormalParameter ) ) ':' )
        // InternalDSLPolicies.g:4060:7: ( ( ruleJvmFormalParameter ) ) ':'
        {
        // InternalDSLPolicies.g:4060:7: ( ( ruleJvmFormalParameter ) )
        // InternalDSLPolicies.g:4061:8: ( ruleJvmFormalParameter )
        {
        // InternalDSLPolicies.g:4061:8: ( ruleJvmFormalParameter )
        // InternalDSLPolicies.g:4062:9: ruleJvmFormalParameter
        {
        pushFollow(FOLLOW_62);
        ruleJvmFormalParameter();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        match(input,80,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred27_InternalDSLPolicies

    // $ANTLR start synpred29_InternalDSLPolicies
    public final void synpred29_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:4877:5: ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )
        // InternalDSLPolicies.g:4877:6: ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) )
        {
        // InternalDSLPolicies.g:4877:6: ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) )
        // InternalDSLPolicies.g:4878:6: ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) )
        {
        // InternalDSLPolicies.g:4878:6: ( ( ruleJvmTypeReference ) )
        // InternalDSLPolicies.g:4879:7: ( ruleJvmTypeReference )
        {
        // InternalDSLPolicies.g:4879:7: ( ruleJvmTypeReference )
        // InternalDSLPolicies.g:4880:8: ruleJvmTypeReference
        {
        pushFollow(FOLLOW_20);
        ruleJvmTypeReference();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalDSLPolicies.g:4883:6: ( ( ruleValidID ) )
        // InternalDSLPolicies.g:4884:7: ( ruleValidID )
        {
        // InternalDSLPolicies.g:4884:7: ( ruleValidID )
        // InternalDSLPolicies.g:4885:8: ruleValidID
        {
        pushFollow(FOLLOW_2);
        ruleValidID();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred29_InternalDSLPolicies

    // $ANTLR start synpred30_InternalDSLPolicies
    public final void synpred30_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:5189:5: ( ( '(' ) )
        // InternalDSLPolicies.g:5189:6: ( '(' )
        {
        // InternalDSLPolicies.g:5189:6: ( '(' )
        // InternalDSLPolicies.g:5190:6: '('
        {
        match(input,23,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred30_InternalDSLPolicies

    // $ANTLR start synpred31_InternalDSLPolicies
    public final void synpred31_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:5208:6: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )
        // InternalDSLPolicies.g:5208:7: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        {
        // InternalDSLPolicies.g:5208:7: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        // InternalDSLPolicies.g:5209:7: () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) )
        {
        // InternalDSLPolicies.g:5209:7: ()
        // InternalDSLPolicies.g:5210:7: 
        {
        }

        // InternalDSLPolicies.g:5211:7: ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )?
        int alt136=2;
        int LA136_0 = input.LA(1);

        if ( (LA136_0==RULE_ID||LA136_0==23||LA136_0==54) ) {
            alt136=1;
        }
        switch (alt136) {
            case 1 :
                // InternalDSLPolicies.g:5212:8: ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )*
                {
                // InternalDSLPolicies.g:5212:8: ( ( ruleJvmFormalParameter ) )
                // InternalDSLPolicies.g:5213:9: ( ruleJvmFormalParameter )
                {
                // InternalDSLPolicies.g:5213:9: ( ruleJvmFormalParameter )
                // InternalDSLPolicies.g:5214:10: ruleJvmFormalParameter
                {
                pushFollow(FOLLOW_55);
                ruleJvmFormalParameter();

                state._fsp--;
                if (state.failed) return ;

                }


                }

                // InternalDSLPolicies.g:5217:8: ( ',' ( ( ruleJvmFormalParameter ) ) )*
                loop135:
                do {
                    int alt135=2;
                    int LA135_0 = input.LA(1);

                    if ( (LA135_0==70) ) {
                        alt135=1;
                    }


                    switch (alt135) {
                	case 1 :
                	    // InternalDSLPolicies.g:5218:9: ',' ( ( ruleJvmFormalParameter ) )
                	    {
                	    match(input,70,FOLLOW_30); if (state.failed) return ;
                	    // InternalDSLPolicies.g:5219:9: ( ( ruleJvmFormalParameter ) )
                	    // InternalDSLPolicies.g:5220:10: ( ruleJvmFormalParameter )
                	    {
                	    // InternalDSLPolicies.g:5220:10: ( ruleJvmFormalParameter )
                	    // InternalDSLPolicies.g:5221:11: ruleJvmFormalParameter
                	    {
                	    pushFollow(FOLLOW_55);
                	    ruleJvmFormalParameter();

                	    state._fsp--;
                	    if (state.failed) return ;

                	    }


                	    }


                	    }
                	    break;

                	default :
                	    break loop135;
                    }
                } while (true);


                }
                break;

        }

        // InternalDSLPolicies.g:5226:7: ( ( '|' ) )
        // InternalDSLPolicies.g:5227:8: ( '|' )
        {
        // InternalDSLPolicies.g:5227:8: ( '|' )
        // InternalDSLPolicies.g:5228:9: '|'
        {
        match(input,76,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred31_InternalDSLPolicies

    // $ANTLR start synpred32_InternalDSLPolicies
    public final void synpred32_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:5305:4: ( ( () '[' ) )
        // InternalDSLPolicies.g:5305:5: ( () '[' )
        {
        // InternalDSLPolicies.g:5305:5: ( () '[' )
        // InternalDSLPolicies.g:5306:5: () '['
        {
        // InternalDSLPolicies.g:5306:5: ()
        // InternalDSLPolicies.g:5307:5: 
        {
        }

        match(input,74,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred32_InternalDSLPolicies

    // $ANTLR start synpred33_InternalDSLPolicies
    public final void synpred33_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:5465:5: ( '<' )
        // InternalDSLPolicies.g:5465:6: '<'
        {
        match(input,41,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred33_InternalDSLPolicies

    // $ANTLR start synpred34_InternalDSLPolicies
    public final void synpred34_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:5522:5: ( ( '(' ) )
        // InternalDSLPolicies.g:5522:6: ( '(' )
        {
        // InternalDSLPolicies.g:5522:6: ( '(' )
        // InternalDSLPolicies.g:5523:6: '('
        {
        match(input,23,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred34_InternalDSLPolicies

    // $ANTLR start synpred35_InternalDSLPolicies
    public final void synpred35_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:5541:6: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )
        // InternalDSLPolicies.g:5541:7: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        {
        // InternalDSLPolicies.g:5541:7: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        // InternalDSLPolicies.g:5542:7: () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) )
        {
        // InternalDSLPolicies.g:5542:7: ()
        // InternalDSLPolicies.g:5543:7: 
        {
        }

        // InternalDSLPolicies.g:5544:7: ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )?
        int alt138=2;
        int LA138_0 = input.LA(1);

        if ( (LA138_0==RULE_ID||LA138_0==23||LA138_0==54) ) {
            alt138=1;
        }
        switch (alt138) {
            case 1 :
                // InternalDSLPolicies.g:5545:8: ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )*
                {
                // InternalDSLPolicies.g:5545:8: ( ( ruleJvmFormalParameter ) )
                // InternalDSLPolicies.g:5546:9: ( ruleJvmFormalParameter )
                {
                // InternalDSLPolicies.g:5546:9: ( ruleJvmFormalParameter )
                // InternalDSLPolicies.g:5547:10: ruleJvmFormalParameter
                {
                pushFollow(FOLLOW_55);
                ruleJvmFormalParameter();

                state._fsp--;
                if (state.failed) return ;

                }


                }

                // InternalDSLPolicies.g:5550:8: ( ',' ( ( ruleJvmFormalParameter ) ) )*
                loop137:
                do {
                    int alt137=2;
                    int LA137_0 = input.LA(1);

                    if ( (LA137_0==70) ) {
                        alt137=1;
                    }


                    switch (alt137) {
                	case 1 :
                	    // InternalDSLPolicies.g:5551:9: ',' ( ( ruleJvmFormalParameter ) )
                	    {
                	    match(input,70,FOLLOW_30); if (state.failed) return ;
                	    // InternalDSLPolicies.g:5552:9: ( ( ruleJvmFormalParameter ) )
                	    // InternalDSLPolicies.g:5553:10: ( ruleJvmFormalParameter )
                	    {
                	    // InternalDSLPolicies.g:5553:10: ( ruleJvmFormalParameter )
                	    // InternalDSLPolicies.g:5554:11: ruleJvmFormalParameter
                	    {
                	    pushFollow(FOLLOW_55);
                	    ruleJvmFormalParameter();

                	    state._fsp--;
                	    if (state.failed) return ;

                	    }


                	    }


                	    }
                	    break;

                	default :
                	    break loop137;
                    }
                } while (true);


                }
                break;

        }

        // InternalDSLPolicies.g:5559:7: ( ( '|' ) )
        // InternalDSLPolicies.g:5560:8: ( '|' )
        {
        // InternalDSLPolicies.g:5560:8: ( '|' )
        // InternalDSLPolicies.g:5561:9: '|'
        {
        match(input,76,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred35_InternalDSLPolicies

    // $ANTLR start synpred36_InternalDSLPolicies
    public final void synpred36_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:5638:4: ( ( () '[' ) )
        // InternalDSLPolicies.g:5638:5: ( () '[' )
        {
        // InternalDSLPolicies.g:5638:5: ( () '[' )
        // InternalDSLPolicies.g:5639:5: () '['
        {
        // InternalDSLPolicies.g:5639:5: ()
        // InternalDSLPolicies.g:5640:5: 
        {
        }

        match(input,74,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred36_InternalDSLPolicies

    // $ANTLR start synpred37_InternalDSLPolicies
    public final void synpred37_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:5981:4: ( 'extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING )
        // InternalDSLPolicies.g:
        {
        if ( input.LA(1)==RULE_ID||(input.LA(1)>=RULE_INT && input.LA(1)<=RULE_DECIMAL)||input.LA(1)==23||input.LA(1)==41||(input.LA(1)>=57 && input.LA(1)<=58)||input.LA(1)==63||(input.LA(1)>=71 && input.LA(1)<=72)||input.LA(1)==74||input.LA(1)==77||input.LA(1)==79||(input.LA(1)>=83 && input.LA(1)<=85)||(input.LA(1)>=88 && input.LA(1)<=100)||input.LA(1)==102 ) {
            input.consume();
            state.errorRecovery=false;state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }
    }
    // $ANTLR end synpred37_InternalDSLPolicies

    // $ANTLR start synpred38_InternalDSLPolicies
    public final void synpred38_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:6052:6: ( 'catch' )
        // InternalDSLPolicies.g:6052:7: 'catch'
        {
        match(input,103,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred38_InternalDSLPolicies

    // $ANTLR start synpred39_InternalDSLPolicies
    public final void synpred39_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:6073:7: ( 'finally' )
        // InternalDSLPolicies.g:6073:8: 'finally'
        {
        match(input,101,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred39_InternalDSLPolicies

    // $ANTLR start synpred42_InternalDSLPolicies
    public final void synpred42_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:6317:5: ( '.' )
        // InternalDSLPolicies.g:6317:6: '.'
        {
        match(input,67,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred42_InternalDSLPolicies

    // $ANTLR start synpred43_InternalDSLPolicies
    public final void synpred43_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:6443:5: ( ( () ruleArrayBrackets ) )
        // InternalDSLPolicies.g:6443:6: ( () ruleArrayBrackets )
        {
        // InternalDSLPolicies.g:6443:6: ( () ruleArrayBrackets )
        // InternalDSLPolicies.g:6444:6: () ruleArrayBrackets
        {
        // InternalDSLPolicies.g:6444:6: ()
        // InternalDSLPolicies.g:6445:6: 
        {
        }

        pushFollow(FOLLOW_2);
        ruleArrayBrackets();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred43_InternalDSLPolicies

    // $ANTLR start synpred44_InternalDSLPolicies
    public final void synpred44_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:6640:5: ( '<' )
        // InternalDSLPolicies.g:6640:6: '<'
        {
        match(input,41,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred44_InternalDSLPolicies

    // $ANTLR start synpred45_InternalDSLPolicies
    public final void synpred45_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:6696:6: ( ( () '.' ) )
        // InternalDSLPolicies.g:6696:7: ( () '.' )
        {
        // InternalDSLPolicies.g:6696:7: ( () '.' )
        // InternalDSLPolicies.g:6697:7: () '.'
        {
        // InternalDSLPolicies.g:6697:7: ()
        // InternalDSLPolicies.g:6698:7: 
        {
        }

        match(input,67,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred45_InternalDSLPolicies

    // $ANTLR start synpred46_InternalDSLPolicies
    public final void synpred46_InternalDSLPolicies_fragment() throws RecognitionException {   
        // InternalDSLPolicies.g:6734:7: ( '<' )
        // InternalDSLPolicies.g:6734:8: '<'
        {
        match(input,41,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred46_InternalDSLPolicies

    // Delegated rules

    public final boolean synpred37_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred37_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred45_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred45_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred29_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred29_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred34_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred34_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred42_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred42_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred31_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred31_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred25_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred36_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred36_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred44_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred44_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred33_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred33_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred30_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred30_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred38_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred38_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred46_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred46_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred35_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred35_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred39_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred39_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred43_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred43_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred32_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred32_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalDSLPolicies() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalDSLPolicies_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA2 dfa2 = new DFA2(this);
    protected DFA17 dfa17 = new DFA17(this);
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA30 dfa30 = new DFA30(this);
    protected DFA46 dfa46 = new DFA46(this);
    protected DFA45 dfa45 = new DFA45(this);
    protected DFA47 dfa47 = new DFA47(this);
    protected DFA49 dfa49 = new DFA49(this);
    protected DFA58 dfa58 = new DFA58(this);
    protected DFA65 dfa65 = new DFA65(this);
    protected DFA64 dfa64 = new DFA64(this);
    protected DFA87 dfa87 = new DFA87(this);
    protected DFA86 dfa86 = new DFA86(this);
    protected DFA88 dfa88 = new DFA88(this);
    protected DFA92 dfa92 = new DFA92(this);
    protected DFA95 dfa95 = new DFA95(this);
    protected DFA94 dfa94 = new DFA94(this);
    protected DFA96 dfa96 = new DFA96(this);
    protected DFA99 dfa99 = new DFA99(this);
    protected DFA117 dfa117 = new DFA117(this);
    protected DFA115 dfa115 = new DFA115(this);
    protected DFA124 dfa124 = new DFA124(this);
    static final String dfa_1s = "\4\uffff";
    static final String dfa_2s = "\2\3\2\uffff";
    static final String dfa_3s = "\2\4\2\uffff";
    static final String dfa_4s = "\2\6\2\uffff";
    static final String dfa_5s = "\2\uffff\1\1\1\2";
    static final String dfa_6s = "\4\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\2\2",
            "\1\1\2\2",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "78:2: ( ( ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )? ( (this_NEWLINE_2= RULE_NEWLINE )* ( (lv_graphPolicies_3_0= ruleGraphPolicies ) ) )+ ) | (this_NEWLINE_4= RULE_NEWLINE )* )";
        }
    }
    static final String[] dfa_8s = {
            "\1\1\1\2\1\3",
            "\1\1\1\2\1\3",
            "",
            ""
    };
    static final short[][] dfa_8 = unpackEncodedStringArray(dfa_8s);

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_8;
        }
        public String getDescription() {
            return "80:4: ( (this_NEWLINE_0= RULE_NEWLINE )* ruleComment )?";
        }
    }
    static final String dfa_9s = "\12\uffff";
    static final String dfa_10s = "\1\10\11\uffff";
    static final String dfa_11s = "\1\6\7\0\2\uffff";
    static final String dfa_12s = "\1\147\7\0\2\uffff";
    static final String dfa_13s = "\10\uffff\1\2\1\1";
    static final String dfa_14s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\0\1\6\2\uffff}>";
    static final String[] dfa_15s = {
            "\1\10\2\uffff\4\10\11\uffff\3\10\13\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\41\10\1\uffff\33\10",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final short[] dfa_10 = DFA.unpackEncodedString(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final char[] dfa_12 = DFA.unpackEncodedStringToUnsignedChars(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final short[][] dfa_15 = unpackEncodedStringArray(dfa_15s);

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = dfa_9;
            this.eof = dfa_10;
            this.min = dfa_11;
            this.max = dfa_12;
            this.accept = dfa_13;
            this.special = dfa_14;
            this.transition = dfa_15;
        }
        public String getDescription() {
            return "1178:4: ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA17_6 = input.LA(1);

                         
                        int index17_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalDSLPolicies()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index17_6);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA17_1 = input.LA(1);

                         
                        int index17_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalDSLPolicies()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index17_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA17_2 = input.LA(1);

                         
                        int index17_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalDSLPolicies()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index17_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA17_3 = input.LA(1);

                         
                        int index17_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalDSLPolicies()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index17_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA17_4 = input.LA(1);

                         
                        int index17_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalDSLPolicies()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index17_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA17_5 = input.LA(1);

                         
                        int index17_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalDSLPolicies()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index17_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA17_7 = input.LA(1);

                         
                        int index17_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalDSLPolicies()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index17_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 17, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_16s = "\13\uffff";
    static final String dfa_17s = "\1\1\12\uffff";
    static final String dfa_18s = "\1\6\1\uffff\10\0\1\uffff";
    static final String dfa_19s = "\1\147\1\uffff\10\0\1\uffff";
    static final String dfa_20s = "\1\uffff\1\2\10\uffff\1\1";
    static final String dfa_21s = "\2\uffff\1\4\1\1\1\2\1\3\1\5\1\0\1\6\1\7\1\uffff}>";
    static final String[] dfa_22s = {
            "\1\1\2\uffff\4\1\11\uffff\3\1\13\uffff\5\1\1\2\1\3\10\1\1\4\1\5\1\6\1\7\1\10\1\11\23\1\1\uffff\33\1",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final char[] dfa_19 = DFA.unpackEncodedStringToUnsignedChars(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[][] dfa_22 = unpackEncodedStringArray(dfa_22s);

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_16;
            this.eof = dfa_17;
            this.min = dfa_18;
            this.max = dfa_19;
            this.accept = dfa_20;
            this.special = dfa_21;
            this.transition = dfa_22;
        }
        public String getDescription() {
            return "()* loopback of 1892:3: ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_7 = input.LA(1);

                         
                        int index27_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalDSLPolicies()) ) {s = 10;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA27_3 = input.LA(1);

                         
                        int index27_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalDSLPolicies()) ) {s = 10;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA27_4 = input.LA(1);

                         
                        int index27_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalDSLPolicies()) ) {s = 10;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA27_5 = input.LA(1);

                         
                        int index27_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalDSLPolicies()) ) {s = 10;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA27_2 = input.LA(1);

                         
                        int index27_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalDSLPolicies()) ) {s = 10;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_2);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA27_6 = input.LA(1);

                         
                        int index27_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalDSLPolicies()) ) {s = 10;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA27_8 = input.LA(1);

                         
                        int index27_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalDSLPolicies()) ) {s = 10;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA27_9 = input.LA(1);

                         
                        int index27_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalDSLPolicies()) ) {s = 10;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index27_9);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 27, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_23s = "\1\51\2\uffff\1\52\7\uffff";
    static final String dfa_24s = "\1\70\2\uffff\1\65\7\uffff";
    static final String dfa_25s = "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\7\1\10\1\11\1\3\1\6";
    static final String dfa_26s = "\13\uffff}>";
    static final String[] dfa_27s = {
            "\1\6\1\3\10\uffff\1\1\1\2\1\4\1\5\1\7\1\10",
            "",
            "",
            "\1\12\12\uffff\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = dfa_16;
            this.eof = dfa_16;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "1968:2: (kw= '->' | kw= '..<' | (kw= '>' kw= '..' ) | kw= '..' | kw= '=>' | (kw= '>' ( ( ( ( '>' '>' ) )=> (kw= '>' kw= '>' ) ) | kw= '>' ) ) | (kw= '<' ( ( ( ( '<' '<' ) )=> (kw= '<' kw= '<' ) ) | kw= '<' | kw= '=>' ) ) | kw= '<>' | kw= '?:' )";
        }
    }
    static final String dfa_28s = "\116\uffff";
    static final String dfa_29s = "\1\2\115\uffff";
    static final String dfa_30s = "\1\6\1\0\114\uffff";
    static final String dfa_31s = "\1\147\1\0\114\uffff";
    static final String dfa_32s = "\2\uffff\1\2\112\uffff\1\1";
    static final String dfa_33s = "\1\uffff\1\0\114\uffff}>";
    static final String[] dfa_34s = {
            "\1\2\2\uffff\4\2\11\uffff\1\2\1\1\1\2\13\uffff\50\2\1\uffff\33\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[][] dfa_34 = unpackEncodedStringArray(dfa_34s);

    class DFA46 extends DFA {

        public DFA46(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 46;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "2869:5: ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA46_1 = input.LA(1);

                         
                        int index46_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index46_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 46, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_35s = "\44\uffff";
    static final String dfa_36s = "\1\6\2\0\41\uffff";
    static final String dfa_37s = "\1\146\2\0\41\uffff";
    static final String dfa_38s = "\3\uffff\2\1\1\2\35\uffff\1\3";
    static final String dfa_39s = "\1\0\1\1\1\2\41\uffff}>";
    static final String[] dfa_40s = {
            "\1\1\2\uffff\4\5\12\uffff\1\2\1\43\20\uffff\1\5\14\uffff\1\3\2\uffff\2\5\4\uffff\1\5\7\uffff\2\5\1\uffff\1\5\1\uffff\1\4\1\5\1\uffff\1\5\3\uffff\3\5\2\uffff\15\5\1\uffff\1\5",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final char[] dfa_36 = DFA.unpackEncodedStringToUnsignedChars(dfa_36s);
    static final char[] dfa_37 = DFA.unpackEncodedStringToUnsignedChars(dfa_37s);
    static final short[] dfa_38 = DFA.unpackEncodedString(dfa_38s);
    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[][] dfa_40 = unpackEncodedStringArray(dfa_40s);

    class DFA45 extends DFA {

        public DFA45(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 45;
            this.eot = dfa_35;
            this.eof = dfa_35;
            this.min = dfa_36;
            this.max = dfa_37;
            this.accept = dfa_38;
            this.special = dfa_39;
            this.transition = dfa_40;
        }
        public String getDescription() {
            return "2888:6: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA45_0 = input.LA(1);

                         
                        int index45_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA45_0==RULE_ID) ) {s = 1;}

                        else if ( (LA45_0==23) ) {s = 2;}

                        else if ( (LA45_0==54) && (synpred17_InternalDSLPolicies())) {s = 3;}

                        else if ( (LA45_0==76) && (synpred17_InternalDSLPolicies())) {s = 4;}

                        else if ( ((LA45_0>=RULE_INT && LA45_0<=RULE_DECIMAL)||LA45_0==41||(LA45_0>=57 && LA45_0<=58)||LA45_0==63||(LA45_0>=71 && LA45_0<=72)||LA45_0==74||LA45_0==77||LA45_0==79||(LA45_0>=83 && LA45_0<=85)||(LA45_0>=88 && LA45_0<=100)||LA45_0==102) ) {s = 5;}

                        else if ( (LA45_0==24) ) {s = 35;}

                         
                        input.seek(index45_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA45_1 = input.LA(1);

                         
                        int index45_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_InternalDSLPolicies()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index45_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA45_2 = input.LA(1);

                         
                        int index45_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_InternalDSLPolicies()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index45_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 45, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String[] dfa_41s = {
            "\1\2\2\uffff\4\2\11\uffff\3\2\13\uffff\46\2\1\1\1\2\1\uffff\33\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };
    static final short[][] dfa_41 = unpackEncodedStringArray(dfa_41s);

    class DFA47 extends DFA {

        public DFA47(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 47;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_41;
        }
        public String getDescription() {
            return "2986:5: ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA47_1 = input.LA(1);

                         
                        int index47_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index47_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 47, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_42s = "\40\uffff";
    static final String dfa_43s = "\1\6\26\uffff\1\0\10\uffff";
    static final String dfa_44s = "\1\146\26\uffff\1\0\10\uffff";
    static final String dfa_45s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\6\uffff\1\6\11\uffff\1\7\1\uffff\1\12\1\13\1\14\1\15\1\16\1\17\1\10\1\11";
    static final String dfa_46s = "\1\0\26\uffff\1\1\10\uffff}>";
    static final String[] dfa_47s = {
            "\1\5\2\uffff\4\14\12\uffff\1\35\21\uffff\1\5\35\uffff\1\14\1\2\1\uffff\1\14\2\uffff\1\26\1\uffff\1\3\3\uffff\1\27\1\30\1\31\2\uffff\5\5\1\1\4\14\1\32\1\33\1\34\1\uffff\1\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_42 = DFA.unpackEncodedString(dfa_42s);
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final char[] dfa_44 = DFA.unpackEncodedStringToUnsignedChars(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final short[][] dfa_47 = unpackEncodedStringArray(dfa_47s);

    class DFA49 extends DFA {

        public DFA49(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 49;
            this.eot = dfa_42;
            this.eof = dfa_42;
            this.min = dfa_43;
            this.max = dfa_44;
            this.accept = dfa_45;
            this.special = dfa_46;
            this.transition = dfa_47;
        }
        public String getDescription() {
            return "3031:2: (this_XConstructorCall_0= ruleXConstructorCall | this_XBlockExpression_1= ruleXBlockExpression | this_XSwitchExpression_2= ruleXSwitchExpression | ( ( ( () 'synchronized' '(' ) )=>this_XSynchronizedExpression_3= ruleXSynchronizedExpression ) | this_XFeatureCall_4= ruleXFeatureCall | this_XLiteral_5= ruleXLiteral | this_XIfExpression_6= ruleXIfExpression | ( ( ( () 'for' '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=>this_XForLoopExpression_7= ruleXForLoopExpression ) | this_XBasicForLoopExpression_8= ruleXBasicForLoopExpression | this_XWhileExpression_9= ruleXWhileExpression | this_XDoWhileExpression_10= ruleXDoWhileExpression | this_XThrowExpression_11= ruleXThrowExpression | this_XReturnExpression_12= ruleXReturnExpression | this_XTryCatchFinallyExpression_13= ruleXTryCatchFinallyExpression | this_XParenthesizedExpression_14= ruleXParenthesizedExpression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA49_0 = input.LA(1);

                         
                        int index49_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA49_0==93) ) {s = 1;}

                        else if ( (LA49_0==72) ) {s = 2;}

                        else if ( (LA49_0==79) ) {s = 3;}

                        else if ( (LA49_0==102) && (synpred19_InternalDSLPolicies())) {s = 4;}

                        else if ( (LA49_0==RULE_ID||LA49_0==41||(LA49_0>=88 && LA49_0<=92)) ) {s = 5;}

                        else if ( ((LA49_0>=RULE_INT && LA49_0<=RULE_DECIMAL)||LA49_0==71||LA49_0==74||(LA49_0>=94 && LA49_0<=97)) ) {s = 12;}

                        else if ( (LA49_0==77) ) {s = 22;}

                        else if ( (LA49_0==83) ) {s = 23;}

                        else if ( (LA49_0==84) ) {s = 24;}

                        else if ( (LA49_0==85) ) {s = 25;}

                        else if ( (LA49_0==98) ) {s = 26;}

                        else if ( (LA49_0==99) ) {s = 27;}

                        else if ( (LA49_0==100) ) {s = 28;}

                        else if ( (LA49_0==23) ) {s = 29;}

                         
                        input.seek(index49_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA49_23 = input.LA(1);

                         
                        int index49_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_InternalDSLPolicies()) ) {s = 30;}

                        else if ( (true) ) {s = 31;}

                         
                        input.seek(index49_23);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 49, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_48s = "\46\uffff";
    static final String dfa_49s = "\1\6\2\0\43\uffff";
    static final String dfa_50s = "\1\146\2\0\43\uffff";
    static final String dfa_51s = "\3\uffff\2\1\1\2\40\uffff";
    static final String dfa_52s = "\1\0\1\1\1\2\43\uffff}>";
    static final String[] dfa_53s = {
            "\1\1\2\uffff\4\5\12\uffff\1\2\21\uffff\1\5\14\uffff\1\3\2\uffff\2\5\4\uffff\1\5\7\uffff\2\5\1\uffff\2\5\1\4\1\5\1\uffff\1\5\3\uffff\22\5\1\uffff\1\5",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final char[] dfa_49 = DFA.unpackEncodedStringToUnsignedChars(dfa_49s);
    static final char[] dfa_50 = DFA.unpackEncodedStringToUnsignedChars(dfa_50s);
    static final short[] dfa_51 = DFA.unpackEncodedString(dfa_51s);
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final short[][] dfa_53 = unpackEncodedStringArray(dfa_53s);

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = dfa_48;
            this.eof = dfa_48;
            this.min = dfa_49;
            this.max = dfa_50;
            this.accept = dfa_51;
            this.special = dfa_52;
            this.transition = dfa_53;
        }
        public String getDescription() {
            return "3523:3: ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA58_0 = input.LA(1);

                         
                        int index58_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA58_0==RULE_ID) ) {s = 1;}

                        else if ( (LA58_0==23) ) {s = 2;}

                        else if ( (LA58_0==54) && (synpred23_InternalDSLPolicies())) {s = 3;}

                        else if ( (LA58_0==76) && (synpred23_InternalDSLPolicies())) {s = 4;}

                        else if ( ((LA58_0>=RULE_INT && LA58_0<=RULE_DECIMAL)||LA58_0==41||(LA58_0>=57 && LA58_0<=58)||LA58_0==63||(LA58_0>=71 && LA58_0<=72)||(LA58_0>=74 && LA58_0<=75)||LA58_0==77||LA58_0==79||(LA58_0>=83 && LA58_0<=100)||LA58_0==102) ) {s = 5;}

                         
                        input.seek(index58_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA58_1 = input.LA(1);

                         
                        int index58_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalDSLPolicies()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index58_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA58_2 = input.LA(1);

                         
                        int index58_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalDSLPolicies()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index58_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 58, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_54s = "\43\uffff";
    static final String dfa_55s = "\1\6\1\0\41\uffff";
    static final String dfa_56s = "\1\146\1\0\41\uffff";
    static final String dfa_57s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_58s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_59s = {
            "\1\2\2\uffff\4\2\12\uffff\1\1\21\uffff\1\2\14\uffff\1\2\2\uffff\2\2\4\uffff\1\2\7\uffff\2\2\1\uffff\1\2\2\uffff\1\2\1\uffff\1\2\3\uffff\3\2\2\uffff\15\2\1\uffff\1\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_54 = DFA.unpackEncodedString(dfa_54s);
    static final char[] dfa_55 = DFA.unpackEncodedStringToUnsignedChars(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final short[] dfa_57 = DFA.unpackEncodedString(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[][] dfa_59 = unpackEncodedStringArray(dfa_59s);

    class DFA65 extends DFA {

        public DFA65(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 65;
            this.eot = dfa_54;
            this.eof = dfa_54;
            this.min = dfa_55;
            this.max = dfa_56;
            this.accept = dfa_57;
            this.special = dfa_58;
            this.transition = dfa_59;
        }
        public String getDescription() {
            return "3989:3: ( ( ( ( ( '(' ( ( ruleJvmFormalParameter ) ) ':' ) )=> (otherlv_2= '(' ( (lv_declaredParam_3_0= ruleJvmFormalParameter ) ) otherlv_4= ':' ) ) ( (lv_switch_5_0= ruleXExpression ) ) otherlv_6= ')' ) | ( ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )? ( (lv_switch_9_0= ruleXExpression ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA65_1 = input.LA(1);

                         
                        int index65_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalDSLPolicies()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index65_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 65, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_60s = "\42\uffff";
    static final String dfa_61s = "\1\6\2\0\37\uffff";
    static final String dfa_62s = "\1\146\2\0\37\uffff";
    static final String dfa_63s = "\3\uffff\1\1\1\2\35\uffff";
    static final String dfa_64s = "\1\0\1\1\1\2\37\uffff}>";
    static final String[] dfa_65s = {
            "\1\1\2\uffff\4\4\12\uffff\1\2\21\uffff\1\4\14\uffff\1\3\2\uffff\2\4\4\uffff\1\4\7\uffff\2\4\1\uffff\1\4\2\uffff\1\4\1\uffff\1\4\3\uffff\3\4\2\uffff\15\4\1\uffff\1\4",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_60 = DFA.unpackEncodedString(dfa_60s);
    static final char[] dfa_61 = DFA.unpackEncodedStringToUnsignedChars(dfa_61s);
    static final char[] dfa_62 = DFA.unpackEncodedStringToUnsignedChars(dfa_62s);
    static final short[] dfa_63 = DFA.unpackEncodedString(dfa_63s);
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final short[][] dfa_65 = unpackEncodedStringArray(dfa_65s);

    class DFA64 extends DFA {

        public DFA64(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 64;
            this.eot = dfa_60;
            this.eof = dfa_60;
            this.min = dfa_61;
            this.max = dfa_62;
            this.accept = dfa_63;
            this.special = dfa_64;
            this.transition = dfa_65;
        }
        public String getDescription() {
            return "4058:5: ( ( ( ( ( ruleJvmFormalParameter ) ) ':' ) )=> ( ( (lv_declaredParam_7_0= ruleJvmFormalParameter ) ) otherlv_8= ':' ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA64_0 = input.LA(1);

                         
                        int index64_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA64_0==RULE_ID) ) {s = 1;}

                        else if ( (LA64_0==23) ) {s = 2;}

                        else if ( (LA64_0==54) && (synpred27_InternalDSLPolicies())) {s = 3;}

                        else if ( ((LA64_0>=RULE_INT && LA64_0<=RULE_DECIMAL)||LA64_0==41||(LA64_0>=57 && LA64_0<=58)||LA64_0==63||(LA64_0>=71 && LA64_0<=72)||LA64_0==74||LA64_0==77||LA64_0==79||(LA64_0>=83 && LA64_0<=85)||(LA64_0>=88 && LA64_0<=100)||LA64_0==102) ) {s = 4;}

                         
                        input.seek(index64_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA64_1 = input.LA(1);

                         
                        int index64_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_InternalDSLPolicies()) ) {s = 3;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index64_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA64_2 = input.LA(1);

                         
                        int index64_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_InternalDSLPolicies()) ) {s = 3;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index64_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 64, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA87 extends DFA {

        public DFA87(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 87;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "5187:3: ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA87_1 = input.LA(1);

                         
                        int index87_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred30_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index87_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 87, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA86 extends DFA {

        public DFA86(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 86;
            this.eot = dfa_35;
            this.eof = dfa_35;
            this.min = dfa_36;
            this.max = dfa_37;
            this.accept = dfa_38;
            this.special = dfa_39;
            this.transition = dfa_40;
        }
        public String getDescription() {
            return "5206:4: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA86_0 = input.LA(1);

                         
                        int index86_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA86_0==RULE_ID) ) {s = 1;}

                        else if ( (LA86_0==23) ) {s = 2;}

                        else if ( (LA86_0==54) && (synpred31_InternalDSLPolicies())) {s = 3;}

                        else if ( (LA86_0==76) && (synpred31_InternalDSLPolicies())) {s = 4;}

                        else if ( ((LA86_0>=RULE_INT && LA86_0<=RULE_DECIMAL)||LA86_0==41||(LA86_0>=57 && LA86_0<=58)||LA86_0==63||(LA86_0>=71 && LA86_0<=72)||LA86_0==74||LA86_0==77||LA86_0==79||(LA86_0>=83 && LA86_0<=85)||(LA86_0>=88 && LA86_0<=100)||LA86_0==102) ) {s = 5;}

                        else if ( (LA86_0==24) ) {s = 35;}

                         
                        input.seek(index86_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA86_1 = input.LA(1);

                         
                        int index86_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred31_InternalDSLPolicies()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index86_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA86_2 = input.LA(1);

                         
                        int index86_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred31_InternalDSLPolicies()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index86_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 86, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA88 extends DFA {

        public DFA88(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 88;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_41;
        }
        public String getDescription() {
            return "5304:3: ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA88_1 = input.LA(1);

                         
                        int index88_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred32_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index88_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 88, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String[] dfa_66s = {
            "\1\2\2\uffff\4\2\11\uffff\3\2\13\uffff\5\2\1\1\42\2\1\uffff\33\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };
    static final short[][] dfa_66 = unpackEncodedStringArray(dfa_66s);

    class DFA92 extends DFA {

        public DFA92(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 92;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_66;
        }
        public String getDescription() {
            return "5463:3: ( ( ( '<' )=>otherlv_3= '<' ) ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) (otherlv_5= ',' ( (lv_typeArguments_6_0= ruleJvmArgumentTypeReference ) ) )* otherlv_7= '>' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA92_1 = input.LA(1);

                         
                        int index92_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred33_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index92_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 92, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA95 extends DFA {

        public DFA95(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 95;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "5520:3: ( ( ( ( '(' ) )=> (lv_explicitConstructorCall_8_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )? otherlv_13= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA95_1 = input.LA(1);

                         
                        int index95_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred34_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index95_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 95, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA94 extends DFA {

        public DFA94(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 94;
            this.eot = dfa_35;
            this.eof = dfa_35;
            this.min = dfa_36;
            this.max = dfa_37;
            this.accept = dfa_38;
            this.special = dfa_39;
            this.transition = dfa_40;
        }
        public String getDescription() {
            return "5539:4: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_arguments_9_0= ruleXShortClosure ) ) | ( ( (lv_arguments_10_0= ruleXExpression ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleXExpression ) ) )* ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA94_0 = input.LA(1);

                         
                        int index94_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA94_0==RULE_ID) ) {s = 1;}

                        else if ( (LA94_0==23) ) {s = 2;}

                        else if ( (LA94_0==54) && (synpred35_InternalDSLPolicies())) {s = 3;}

                        else if ( (LA94_0==76) && (synpred35_InternalDSLPolicies())) {s = 4;}

                        else if ( ((LA94_0>=RULE_INT && LA94_0<=RULE_DECIMAL)||LA94_0==41||(LA94_0>=57 && LA94_0<=58)||LA94_0==63||(LA94_0>=71 && LA94_0<=72)||LA94_0==74||LA94_0==77||LA94_0==79||(LA94_0>=83 && LA94_0<=85)||(LA94_0>=88 && LA94_0<=100)||LA94_0==102) ) {s = 5;}

                        else if ( (LA94_0==24) ) {s = 35;}

                         
                        input.seek(index94_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA94_1 = input.LA(1);

                         
                        int index94_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred35_InternalDSLPolicies()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index94_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA94_2 = input.LA(1);

                         
                        int index94_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred35_InternalDSLPolicies()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index94_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 94, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA96 extends DFA {

        public DFA96(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 96;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_41;
        }
        public String getDescription() {
            return "5637:3: ( ( ( () '[' ) )=> (lv_arguments_14_0= ruleXClosure ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA96_1 = input.LA(1);

                         
                        int index96_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred36_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index96_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 96, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_67s = "\1\41\115\uffff";
    static final String dfa_68s = "\1\6\40\0\55\uffff";
    static final String dfa_69s = "\1\147\40\0\55\uffff";
    static final String dfa_70s = "\41\uffff\1\2\53\uffff\1\1";
    static final String dfa_71s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\55\uffff}>";
    static final String[] dfa_72s = {
            "\1\1\2\uffff\1\24\1\27\1\23\1\25\11\uffff\1\41\1\40\1\41\13\uffff\5\41\1\15\17\41\1\10\1\7\4\41\1\6\7\41\1\17\1\12\1\41\1\20\1\41\1\uffff\1\31\1\41\1\13\3\41\1\32\1\33\1\34\2\41\1\2\1\3\1\4\1\5\1\16\1\11\1\21\1\22\1\26\1\30\1\35\1\36\1\37\1\41\1\14\1\41",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };
    static final short[] dfa_67 = DFA.unpackEncodedString(dfa_67s);
    static final char[] dfa_68 = DFA.unpackEncodedStringToUnsignedChars(dfa_68s);
    static final char[] dfa_69 = DFA.unpackEncodedStringToUnsignedChars(dfa_69s);
    static final short[] dfa_70 = DFA.unpackEncodedString(dfa_70s);
    static final short[] dfa_71 = DFA.unpackEncodedString(dfa_71s);
    static final short[][] dfa_72 = unpackEncodedStringArray(dfa_72s);

    class DFA99 extends DFA {

        public DFA99(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 99;
            this.eot = dfa_28;
            this.eof = dfa_67;
            this.min = dfa_68;
            this.max = dfa_69;
            this.accept = dfa_70;
            this.special = dfa_71;
            this.transition = dfa_72;
        }
        public String getDescription() {
            return "5980:3: ( ( 'extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING )=> (lv_expression_2_0= ruleXExpression ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA99_1 = input.LA(1);

                         
                        int index99_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA99_2 = input.LA(1);

                         
                        int index99_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA99_3 = input.LA(1);

                         
                        int index99_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA99_4 = input.LA(1);

                         
                        int index99_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA99_5 = input.LA(1);

                         
                        int index99_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA99_6 = input.LA(1);

                         
                        int index99_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA99_7 = input.LA(1);

                         
                        int index99_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA99_8 = input.LA(1);

                         
                        int index99_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA99_9 = input.LA(1);

                         
                        int index99_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA99_10 = input.LA(1);

                         
                        int index99_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA99_11 = input.LA(1);

                         
                        int index99_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA99_12 = input.LA(1);

                         
                        int index99_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_12);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA99_13 = input.LA(1);

                         
                        int index99_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_13);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA99_14 = input.LA(1);

                         
                        int index99_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_14);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA99_15 = input.LA(1);

                         
                        int index99_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_15);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA99_16 = input.LA(1);

                         
                        int index99_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_16);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA99_17 = input.LA(1);

                         
                        int index99_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_17);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA99_18 = input.LA(1);

                         
                        int index99_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_18);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA99_19 = input.LA(1);

                         
                        int index99_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_19);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA99_20 = input.LA(1);

                         
                        int index99_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_20);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA99_21 = input.LA(1);

                         
                        int index99_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_21);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA99_22 = input.LA(1);

                         
                        int index99_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_22);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA99_23 = input.LA(1);

                         
                        int index99_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_23);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA99_24 = input.LA(1);

                         
                        int index99_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_24);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA99_25 = input.LA(1);

                         
                        int index99_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_25);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA99_26 = input.LA(1);

                         
                        int index99_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_26);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA99_27 = input.LA(1);

                         
                        int index99_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_27);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA99_28 = input.LA(1);

                         
                        int index99_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_28);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA99_29 = input.LA(1);

                         
                        int index99_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_29);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA99_30 = input.LA(1);

                         
                        int index99_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_30);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA99_31 = input.LA(1);

                         
                        int index99_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_31);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA99_32 = input.LA(1);

                         
                        int index99_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalDSLPolicies()) ) {s = 77;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index99_32);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 99, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_73s = "\117\uffff";
    static final String dfa_74s = "\1\2\116\uffff";
    static final String dfa_75s = "\1\6\1\0\115\uffff";
    static final String dfa_76s = "\1\151\1\0\115\uffff";
    static final String dfa_77s = "\2\uffff\1\2\113\uffff\1\1";
    static final String dfa_78s = "\1\uffff\1\0\115\uffff}>";
    static final String[] dfa_79s = {
            "\1\2\2\uffff\4\2\11\uffff\3\2\13\uffff\5\2\1\1\42\2\1\uffff\33\2\1\uffff\1\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_73 = DFA.unpackEncodedString(dfa_73s);
    static final short[] dfa_74 = DFA.unpackEncodedString(dfa_74s);
    static final char[] dfa_75 = DFA.unpackEncodedStringToUnsignedChars(dfa_75s);
    static final char[] dfa_76 = DFA.unpackEncodedStringToUnsignedChars(dfa_76s);
    static final short[] dfa_77 = DFA.unpackEncodedString(dfa_77s);
    static final short[] dfa_78 = DFA.unpackEncodedString(dfa_78s);
    static final short[][] dfa_79 = unpackEncodedStringArray(dfa_79s);

    class DFA117 extends DFA {

        public DFA117(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 117;
            this.eot = dfa_73;
            this.eof = dfa_74;
            this.min = dfa_75;
            this.max = dfa_76;
            this.accept = dfa_77;
            this.special = dfa_78;
            this.transition = dfa_79;
        }
        public String getDescription() {
            return "6638:3: ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA117_1 = input.LA(1);

                         
                        int index117_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred44_InternalDSLPolicies()) ) {s = 78;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index117_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 117, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA115 extends DFA {

        public DFA115(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 115;
            this.eot = dfa_73;
            this.eof = dfa_74;
            this.min = dfa_75;
            this.max = dfa_76;
            this.accept = dfa_77;
            this.special = dfa_78;
            this.transition = dfa_79;
        }
        public String getDescription() {
            return "6732:5: ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA115_1 = input.LA(1);

                         
                        int index115_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred46_InternalDSLPolicies()) ) {s = 78;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index115_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 115, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_80s = "\7\uffff";
    static final String dfa_81s = "\2\uffff\1\4\3\uffff\1\4";
    static final String dfa_82s = "\1\6\1\uffff\1\26\1\6\2\uffff\1\26";
    static final String dfa_83s = "\1\131\1\uffff\1\103\1\73\2\uffff\1\103";
    static final String dfa_84s = "\1\uffff\1\1\2\uffff\1\2\1\3\1\uffff";
    static final String dfa_85s = "\7\uffff}>";
    static final String[] dfa_86s = {
            "\1\2\122\uffff\1\1",
            "",
            "\1\4\54\uffff\1\3",
            "\1\6\64\uffff\1\5",
            "",
            "",
            "\1\4\54\uffff\1\3"
    };

    static final short[] dfa_80 = DFA.unpackEncodedString(dfa_80s);
    static final short[] dfa_81 = DFA.unpackEncodedString(dfa_81s);
    static final char[] dfa_82 = DFA.unpackEncodedStringToUnsignedChars(dfa_82s);
    static final char[] dfa_83 = DFA.unpackEncodedStringToUnsignedChars(dfa_83s);
    static final short[] dfa_84 = DFA.unpackEncodedString(dfa_84s);
    static final short[] dfa_85 = DFA.unpackEncodedString(dfa_85s);
    static final short[][] dfa_86 = unpackEncodedStringArray(dfa_86s);

    class DFA124 extends DFA {

        public DFA124(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 124;
            this.eot = dfa_80;
            this.eof = dfa_81;
            this.min = dfa_82;
            this.max = dfa_83;
            this.accept = dfa_84;
            this.special = dfa_85;
            this.transition = dfa_86;
        }
        public String getDescription() {
            return "7194:3: ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000052L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x000000001E180000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x000000001E180002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000FE0000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x8600020000801E40L,0x0000005FFF38A580L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x000007F000000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x00000C0000000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0003C00000000002L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x00040E0000000002L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0040000000800040L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x01F8060000000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0040020000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0600000000000002L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x7800000000000002L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000006L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000038L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000040L,0x000000000F000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000020000000040L,0x000000001F000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0040000000800040L,0x0000010000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000040000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000800002L,0x0000000000000438L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x8640020001801E40L,0x0000005FFF38B580L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000001000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000438L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x8600020000801E40L,0x0000005FFF38A780L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000240L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x8600020000801E40L,0x0000005FFF38AD80L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000840L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x8640020000801E40L,0x0000005FFFF8BD80L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001040L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x8600020000801E40L,0x0000005FFFF8AD80L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x8600020000C01E42L,0x0000005FFFF8A580L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x8600020000801E42L,0x0000005FFFF8A580L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x8640020000801E40L,0x0000005FFF38A580L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0040000000800040L,0x0000000000070240L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000050040L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010040L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x8600020000C01E40L,0x0000005FFFF8A580L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000400000L,0x0000000000000040L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x8600020000801E40L,0x0000005FFFF8A580L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x8600020000C01E40L,0x0000005FFF38A580L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x8600020001801E40L,0x0000005FFF38A580L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x8600020000801E40L,0x0000005FFFF8A780L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x8600020000C01E40L,0x0000005FFFF8A780L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000800002L,0x0000000000000400L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000020000800002L,0x0000000000000400L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000001000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x8600020000801E42L,0x0000005FFF38A580L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000000L,0x000000A000000000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000002L,0x000000A000000000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0040000001800040L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000020000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000002L,0x0000000011000000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000000000002L,0x0000020000000000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000040L,0x0000000002000000L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000000040L,0x0000000008000000L});
    public static final BitSet FOLLOW_95 = new BitSet(new long[]{0x0800000000000040L});
    public static final BitSet FOLLOW_96 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_97 = new BitSet(new long[]{0x0000000000000042L});

}