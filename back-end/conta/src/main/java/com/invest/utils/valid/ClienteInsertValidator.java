package com.invest.utils.valid;

import java.util.ArrayList;
import java.util.List;

import com.invest.dto.ClientNewDTO;
import com.invest.entity.enums.TipoPessoa;
import com.invest.service.exeptions.FieldMessage;
import com.invest.utils.BR;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
	    List<FieldMessage> list = new ArrayList<>();

	    if (objDto.getTipo().equals(TipoPessoa.PESSOA_FISICA) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
	        list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
	    }

	    if (objDto.getTipo().equals(TipoPessoa.PESSOA_JURIDICA) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
	        list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
	    }

	    if (!list.isEmpty()) {
	        context.disableDefaultConstraintViolation();
	        list.forEach(e ->
	            context.buildConstraintViolationWithTemplate(e.getMessage())
	                   .addPropertyNode(e.getFieldName())
	                   .addConstraintViolation()
	        );
	    }

	    return list.isEmpty();
	}

}
