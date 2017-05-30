package org.gw4e.eclipse.facade;

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

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.graphwalker.dsl.antlr.generator.GeneratorFactory;

public class JavaScriptFacade {
	private final static String DEFAULT_SCRIPT_LANGUAGE = "JavaScript";
	 
	Compilable compiler;
	static JavaScriptFacade me = null;
	static {
		me = new JavaScriptFacade();
	}
	public static JavaScriptFacade getInstance () {
		return me;
	}
	
	private JavaScriptFacade()  {
		ScriptEngine engine = getEngineByName();
		compiler = (Compilable) engine;
	}
	
	private String escapeQuotes (String code) {
		return code.replaceAll("\"", "\\\"");
	}
	 
	public String convert (String code) {
		// Try as if we would not have to convert ... The generator coming from GraphWalker annotation does not have to be parsed 
		try {
			GeneratorFactory.parse( code );
			return code;
		} catch (Throwable ignore) {
		}
		 
		String script = "try { load(\"nashorn:mozilla_compat.js\");} catch (e) {}  ; importPackage(Packages.org.graphwalker.core.generator); importPackage(Packages.org.graphwalker.core.condition) ; importPackage(Packages.java.util.concurrent) ;obj = { run: function () { ret = " + escapeQuotes(code) + ".toString(); } } ;  obj.run()  ";
		try {
			CompiledScript compiledScript = compiler.compile(script);
			Bindings bindings = ((ScriptEngine)compiler).getBindings(ScriptContext.ENGINE_SCOPE);
			bindings.put("impl", this);
			compiledScript.eval(bindings);
			ScriptEngine scriptEngine = compiledScript.getEngine();
			String pathgenerator =  (String)scriptEngine.get("ret");
			org.graphwalker.core.generator.PathGenerator  pg  = GeneratorFactory.parse( pathgenerator );
			if (pg!=null) {
				return pathgenerator;
			}
		} catch (javax.script.ScriptException e) {
		} catch (org.graphwalker.dsl.antlr.DslException e) {	
		} catch (Exception ex) {
			ResourceManager.logException(ex, script);
		}
		return null;
	}

	private ScriptEngine getEngineByName() {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName(DEFAULT_SCRIPT_LANGUAGE);
		if (null == engine) {
			throw new RuntimeException("Failed to create ScriptEngine");
		}
		return engine;
	}
 
}
