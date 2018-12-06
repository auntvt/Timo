package com.linln.admin.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
	// 文件名
	private String name;
	// 文件路径
	private String path;
	// 文件类型
	private String mime;
	// 文件大小
	private Long size;
	// 文件md5值
	private String md5;
	// 文件sha1值
	private String sha1;
	// 创建时间
	@CreatedDate
	private Date createDate;
	// 创建者
	@CreatedBy
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_by")
	@JsonIgnore
	private User createBy;
}
