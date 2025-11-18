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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;


public class ArrayAdapter<T> extends TypeAdapter<List<T>> {
  private Class<T> adapterclass;

  public ArrayAdapter(Class<T> adapterclass) {
      this.adapterclass = adapterclass;
  }

  public List<T> read(JsonReader reader) throws IOException {

      List<T> list = new ArrayList<T>();

      Gson gson = new GsonBuilder()
              .registerTypeAdapterFactory(new ArrayAdapterFactory())
              .create();

      if (reader.peek() == JsonToken.BEGIN_OBJECT) {
          T inning = gson.fromJson(reader, adapterclass);
          list.add(inning);

      } else if (reader.peek() == JsonToken.BEGIN_ARRAY) {

          reader.beginArray();
          while (reader.hasNext()) {
              T inning = gson.fromJson(reader, adapterclass);
              list.add(inning);
          }
          reader.endArray();

      }

      return list;
  }

  public void write(JsonWriter writer, List<T> value) throws IOException {

  }
  
}
