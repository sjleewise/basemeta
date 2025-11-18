/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UtilJson.java
 * 2. Package : kr.wise.egmd.util
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 18. 오후 4:26:36
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 18. 		: 신규 개발.
 */
package kr.wise.commons.util;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class ArrayAdapterFactory implements TypeAdapterFactory {

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {

      TypeAdapter<T> typeAdapter = null;

      try {
          if (type.getRawType() == List.class)
              typeAdapter = new ArrayAdapter(
                      (Class) ((ParameterizedType) type.getType())
                              .getActualTypeArguments()[0]);
      } catch (Exception e) {
          e.printStackTrace();
      }

      return typeAdapter;
      

  }

}