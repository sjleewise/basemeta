package kr.wise.commons.cmm.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import kr.wise.commons.cmm.security.RequestWrapper.ServletInputStreamImpl;

public class HttpUtil {

	/**
	 * Request 에서 Body Data를 꺼냄
	 * @param request
	 * @return
	 */
	public String getBodyDataByRequest( HttpServletRequest request) {
		
		String charEncoding = request.getCharacterEncoding(); // 인코딩 설정
		Charset  encoding = StringUtils.isBlank(charEncoding) ? StandardCharsets.UTF_8 : Charset.forName(charEncoding);
		
		String collect = null;
		try {
			
			InputStream is = request.getInputStream();
			byte[] rawData = IOUtils.toByteArray(is); // InputStream 을 별도로 저장한 다음 getReader() 에서 새 스트림으로 생성

			// body 파싱
			collect = new BufferedReader(
					new InputStreamReader( 
							getInputStream( rawData), encoding
							)
					).lines().collect(Collectors.joining(System.lineSeparator()));
			
			if (StringUtils.isEmpty(collect)) { // body 가 없을경우 로깅 제외
				
				return null;
			}
//			if (request.getContentType() != null && request.getContentType().contains(
//					
//				ContentType.MULTIPART_FORM_DATA.getMimeType())) { // 파일 업로드시 로깅제외
//				return null;
//			}

		} catch( Exception e) {
			
			e.printStackTrace();
			
		}
		return collect;
	}
	
	public ServletInputStream getInputStream(byte[] rawData) throws IOException {
 		final ByteArrayInputStream bis = new ByteArrayInputStream(rawData);

 		return new ServletInputStreamImpl(bis);
 	}
	
	class ServletInputStreamImpl extends ServletInputStream{
 		private InputStream is;

 		public ServletInputStreamImpl(InputStream bis){
 			is = bis;
 		}

 		public int read() throws IOException {
 			return is.read();
 		}

 		public int read(byte[] b) throws IOException {
 			return is.read(b);
 		}
 	}
//	private static ServletInputStream getInputStream( byte[] rawData) throws IOException {
//	
//		final ByteArrayInputStream bais = new ByteArrayInputStream( rawData);
//		ServletInputStream sis = new ServletInputStream() {
//			
//			@Override
//			public int read() throws IOException {
//				return bais.read();
//			}
//		};
//		
//		return sis;
//	}
}
