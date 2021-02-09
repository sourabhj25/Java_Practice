package com.phoenix.md5reader.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author Vishal Arora
 *
 */
@Data
@AllArgsConstructor
@ToString(includeFieldNames = true)
@ApiModel(description = "MD5 Code Existence Check")
public class MD5ExistenceCheckDTO {

	@ApiModelProperty(name = "md5Code")
	private String md5Code;

	@ApiModelProperty(name = "existence")
	private boolean existence;

}
