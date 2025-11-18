package kr.wise.commons.cmm.security;

import java.text.SimpleDateFormat;

import kr.wise.commons.helper.CustomJacksonObjectMapper;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class HtmlEscapingObjectMapperFactory implements FactoryBean<ObjectMapper> {
	static final Logger logger = LoggerFactory.getLogger(CustomJacksonObjectMapper.class);
	
    private final ObjectMapper objectMapper;

    public HtmlEscapingObjectMapperFactory() {
        objectMapper = new ObjectMapper();
//        objectMapper.getJsonFactory().setCharacterEscapes(new HTMLCharacterEscapes());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyyMMdd HHmmss"));

		logger.debug("json set");
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public ObjectMapper getObject() throws Exception {
        return objectMapper;
    }

    @Override
    public Class<?> getObjectType() {
        return ObjectMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static class HTMLCharacterEscapes extends CharacterEscapes {
        private final int[] asciiEscapes;
        private final CharSequenceTranslator translator;
        
        public HTMLCharacterEscapes() {
            // start with set of characters known to require escaping (double-quote, backslash etc)
            asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
            
            // and force escaping of a few others:
//            for(int i=0; i<asciiEscapes.length; i++) {
//            	if(asciiEscapes[i] == '<') { // < 60
//            		if(asciiEscapes[i+1] == '/') { // / 47
//            			if((asciiEscapes[i+2]=='s' || asciiEscapes[i+2]=='S') // 83 115 
//            					&& (asciiEscapes[i+3]=='c' || asciiEscapes[i+3]=='C')	//67 99
//            					&& (asciiEscapes[i+4]=='r' || asciiEscapes[i+4]=='R') //82 114
//            					&& (asciiEscapes[i+5]=='i' || asciiEscapes[i+5]=='I') //73 105
//            					&& (asciiEscapes[i+6]=='p' || asciiEscapes[i+6]=='P') //80 112
//            					&& (asciiEscapes[i+7]=='t' || asciiEscapes[i+7]=='T') //84 116
//            					&& asciiEscapes[i+8]=='>') { //script> 62
//            				asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
//            	            asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
//            	            asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
//            	            asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
//            	            asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
//            			}
//            		} else {
//            			if((asciiEscapes[i+1]==83 || asciiEscapes[i+1]==115) 
//            					&& (asciiEscapes[i+2]==67 || asciiEscapes[i+2]==99)
//            					&& (asciiEscapes[i+3]==82 || asciiEscapes[i+3]==114)
//            					&& (asciiEscapes[i+4]==73 || asciiEscapes[i+4]==105)
//            					&& (asciiEscapes[i+5]==80 || asciiEscapes[i+5]==112)
//            					&& (asciiEscapes[i+6]==84 || asciiEscapes[i+6]==116)
//            					&& asciiEscapes[i+7]==62) { //script>
//            				asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
//            	            asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
//            	            asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
//            	            asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
//            	            asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
//            			}
//            		}
//            	}
//            }
            asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
            
         // 2. XSS 방지 처리 특수 문자 인코딩 값 지정
            translator = new AggregateTranslator(
                new LookupTranslator(EntityArrays.BASIC_ESCAPE()),  // <, >, &, " 는 여기에 포함됨
                new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()),
                new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE()),
                // 여기에서 커스터마이징 가능
                new LookupTranslator(  
                    new String[][]{
//                        {"(",  "&#40;"},
//                        {")",  "&#41;"},
//                        {"#",  "&#35;"},
//                        {"\'", "&#39;"}
                    		{"<script>", ""},
                    		{"</script>", ""},
                    		{"alert", ""}
                    }
                )
            );
            
        }


        @Override
        public int[] getEscapeCodesForAscii() {
            return asciiEscapes;
        }

        // and this for others; we don't need anything special here
        @Override
        public SerializableString getEscapeSequence(int ch) {
        	return new SerializedString(translator.translate(Character.toString((char) ch)));
//            return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));

        }
    }
}