package br.com.maddytec.marte.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.maddytec.marte.utils.DirecaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(of = "id")
@Entity
public class Sonda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Min(value = 0, message = "Coordenada X não pode ser menor que 0")
	@Max(value = 9999, message = "Coordenada X não pode ser maior 9999")
	@Column(name = "coordenada_x", nullable = false, length = 4)
	private Long coordenadaX;

	@NotNull
	@Min(value = 0, message = "Coordenada Y não pode ser menor que 0")
	@Max(value = 9999, message = "Coordenada Y não pode ser maior 9999")
	@Column(name = "coordenada_y", nullable = false, length = 4)
	private Long coordenadaY;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DirecaoEnum direcao;
	
}
