package pi.password.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pi.password.config.RuntimeConfig;

public class CgiService {

	private final File SCRIPT_DIR = new File(RuntimeConfig.WORKING_DIRECTORY.toString() + "/cgi");
	private final File EXEC_DIR = new File(RuntimeConfig.WORKING_DIRECTORY.toString());

	public CgiService() {
	}
	
	public String execute(String scriptToRun) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayInputStream bain = new ByteArrayInputStream("".getBytes());
		
		File targetFile;
		try {
			targetFile = new File(SCRIPT_DIR.getCanonicalPath() + "/" + scriptToRun);
			
			execute(targetFile, new String[] {}, bain, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String ret = baos.toString();
		return ret;
	}

	public void execute(File scriptToRun, String[] env, InputStream in, OutputStream out) {
		execute(scriptToRun, new String[]{}, env, in, out);
	}

	public void execute(File scriptToRun, String[] params, String[] env, InputStream in, OutputStream out) {
		Process process = null;
		try {
			String name = scriptToRun.getName();

			process = createProcess(scriptToRun, params, env);

			redirectProcessPipes(process, in, out, name);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (process != null && process.isAlive()){
				try { process.destroy(); } catch (Exception ex) {}
			}
		}
	}

	private void redirectProcessPipes(Process process, InputStream in, OutputStream out, String name)
			throws IOException {
		InputStream processIn = process.getInputStream();
		OutputStream processOut = process.getOutputStream();

		do {
			handleRedirects(process, in, out, processIn, processOut);
		} while (process.isAlive());

		handleRedirects(process, in, out, processIn, processOut);
		
		out.close();
		in.close();
	}

	private void handleRedirects(Process process, InputStream stdIn, OutputStream stdOut, InputStream procIn,
			OutputStream procOut) throws IOException {
		byte[] buffer = new byte[128];
		int read;
		while (stdIn.available() > 0) {
			read = stdIn.read(buffer);
			procOut.write(buffer, 0, read);
		}
		while (procIn.available() > 0) {
			read = procIn.read(buffer);
			stdOut.write(buffer, 0, read);
		}
		while (process.getErrorStream().available() > 0) {
			read = process.getErrorStream().read(buffer);
			System.err.write(buffer, 0, read);
		}
	}

	private Process createProcess(File scriptToRun, String [] params, String[] env) throws IOException {
		String cmd = scriptToRun.getAbsolutePath();
		String args = "";

		if (params != null) {
			for (String it : params) {
				args += " " + it;
			}
		}

		Process process = Runtime.getRuntime().exec(cmd + args, env, EXEC_DIR);
		return process;
	}
	
	public List<String> getCgiScripts() {
		List<String> ret = new ArrayList<>();
		Arrays.asList(SCRIPT_DIR.listFiles()).stream().map(e -> e.getName()).forEach(ret::add);
		return ret;
	}

}
