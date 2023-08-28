/**
 * Copyright © 2005-2015, Alexey Valikov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the FreeBSD Project.
 */
package org.jvnet.jaxb2_commons.tests.one;

import java.util.ArrayList;
import java.util.List;

import org.jvnet.jaxb.maven.AbstractXJC2Mojo;
import org.jvnet.jaxb.maven.test.RunXJC2Mojo;

import com.sun.tools.xjc.Options;

public class RunOnePlugin extends RunXJC2Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo<Options> mojo) {
		super.configureMojo(mojo);
		mojo.setExtension(true);
		mojo.setForceRegenerate(true);
		mojo.setDebug(false);
	}

	@Override
	public List<String> getArgs() {
		final List<String> args = new ArrayList<>(super.getArgs());
		args.add("-XtoString");
		args.add("-Xequals");
		args.add("-XhashCode");
		args.add("-Xcopyable");
		args.add("-Xmergeable");
		args.add("-Xinheritance");
		args.add("-Xsetters");
		args.add("-Xwildcard");
		return args;
	}

}
