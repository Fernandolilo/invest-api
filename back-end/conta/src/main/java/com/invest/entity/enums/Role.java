package com.invest.entity.enums;


public enum Role {	

	
	ADMIN(0, "ROLE_ADMIN"), 
	USER(1, "ROLE_USER")
	;

	private int cod;

	private String descricao;

	Role(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static Role toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (Role x : Role.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
