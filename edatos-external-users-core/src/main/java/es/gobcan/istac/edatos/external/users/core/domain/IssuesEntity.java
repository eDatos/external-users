package es.gobcan.istac.edatos.external.users.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Workplace;
import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;

@Entity
@Table(name = "tb_issues")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class IssuesEntity extends AbstractVersionedAndAuditingEntity {

	private static final long serialVersionUID = -2783257315749213377L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_issues")
    @SequenceGenerator(name = "seq_tb_issues", sequenceName = "seq_tb_issues", allocationSize = 50, initialValue = 1)
	private Long id;
	
	@NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String surname1;

    @Size(max = 255)
    private String surname2;

    @NotNull
    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String subject;
    
    @NotNull
    @Column(nullable = false)
    private String message;
    
    @Enumerated(EnumType.STRING)
    private Workplace workplace;

    @Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname1() {
		return surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Workplace getWorkplace() {
		return workplace;
	}

	public void setWorkplace(Workplace workplace) {
		this.workplace = workplace;
	}
}
