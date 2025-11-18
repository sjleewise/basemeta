package kr.wise.commons.helper.grid;

public class JqGridVO<T> {
	
	private T resultVO;
	
	private int result = 0;
	
	private String message;
	
	private String saction;
	
	
	public JqGridVO() {
		// TODO Auto-generated constructor stub
	}
	
	public JqGridVO(T resultVO, int result, String message, String action) {
		this.resultVO = resultVO;
		this.result		= result;
		this.message	= message;
		this.saction	= action;
	}

	public T getResultVO() {
		return resultVO;
	}

	public void setResultVO(T resultVO) {
		this.resultVO = resultVO;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getSaction() {
		return saction;
	}

	public void setSaction(String saction) {
		this.saction = saction;
	}
	
	

}
