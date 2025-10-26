package com.julianbetov.automatedaudit.mapper;

import com.julianbetov.automatedaudit.dto.request.RegistryRequest;
import com.julianbetov.automatedaudit.dto.response.FormResponse;
import com.julianbetov.automatedaudit.model.Form;
import com.julianbetov.automatedaudit.model.Registry;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = RegistryMapper.class)
public abstract class FormMapper {

    @Named("buildFormFromRegistries")
    public Form buildFormFromRegistries(List<RegistryRequest> registries) {
        if (registries == null) {
            return null;
        }
        Form form = new Form();
        form.setRegistries(registriesToEntities(registries));
        finalizeForm(form);
        return form;
    }

    protected abstract FormResponse toResponse(Form form);

    protected abstract List<Registry> registriesToEntities(List<RegistryRequest> registries);

    @AfterMapping
    protected void finalizeForm(@MappingTarget Form form) {
        if (form.getRegistries() != null) {
            for (Registry r : form.getRegistries()) {
                r.setForm(form);
            }
            form.setQuestionCount(form.getRegistries().size());
        } else {
            form.setQuestionCount(0);
        }
    }

}
