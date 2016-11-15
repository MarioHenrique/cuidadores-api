package br.com.softcare.interceptors;

import java.util.ArrayList;
import java.util.List;

public class PathAllowed {

	private String uri;
	private String method;
	
	public PathAllowed(String uri, String method) {
		this.uri = uri;
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PathAllowed other = (PathAllowed) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	public static List<PathAllowed> allowedPaths(){
		List<PathAllowed> paths = new ArrayList<>();
		paths.add(new PathAllowed("/cuidadores-api/api/user","POST"));
		paths.add(new PathAllowed("/cuidadores-api/user/login","POST"));
		paths.add(new PathAllowed("/api/user","POST"));
		paths.add(new PathAllowed("/api/user/login","POST"));
		return paths;
	}
	
	public static boolean isValidPath(PathAllowed path,List<PathAllowed> allowedPaths){
		return allowedPaths.stream().anyMatch(p-> p.equals(path));
	}
	
}
