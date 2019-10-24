package com.linln.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linln.common.utils.HttpServletUtil;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 小懒虫
 * @date 2018/11/02
 */
@Entity
@Table(name="sys_file")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Upload implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 文件名 */
	private String name;

	/** 文件路径 */
	private String path;

	/** 文件类型 */
	private String mime;

	/** 文件大小 */
	private Long size;

	/** 文件md5值 */
	private String md5;

	/** 文件sha1值 */
	private String sha1;

	/** 创建时间 */
	@CreatedDate
	private Date createDate;

	/** 创建者 */
	@CreatedBy
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name="create_by")
	@JsonIgnore
	private User createBy;

	/**
	 * 获取文件绝对路径
 	 */
	public String getUrl() {
		HttpServletRequest request = HttpServletUtil.getRequest();
		if (!StringUtils.isEmpty(path)) {
			StringBuffer url = request.getRequestURL();
			String baseUrl = url.delete(url.length() - request.getRequestURI().length(), url.length())
					.append(request.getContextPath()).toString();
			return baseUrl + path;
		}
		return path;
	}
}
