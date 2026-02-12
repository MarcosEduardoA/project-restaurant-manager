package br.com.restaurant.manager.utilities;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.restaurant.manager.model.Sale;

@Mapper(componentModel = "spring")
public interface SaleMapper {
	
	/*Atualiza uma entidade 'Sale' existentes com os dados de outra.
	 * @param source O objeto com os novos dados (vindo do formulário)
	 * @param target A entidade que vai ser atualizada (vindo do banco)*/
	
	@Mapping(target = "items", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // Se um campo no objeto de origem (source) for nulo, ele não será copiado para o destino (target)
	void updateSale(Sale source, @MappingTarget Sale target);
}
