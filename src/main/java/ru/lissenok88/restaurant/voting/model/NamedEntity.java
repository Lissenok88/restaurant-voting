package ru.lissenok88.restaurant.voting.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.lissenok88.restaurant.voting.util.validation.NoHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NamedEntity extends BaseEntity {
    @NotBlank
    @Size(min = 2, max = 5000)
    @Column(name = "name", nullable = false)
    @NoHtml
    protected String name;

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + name + ']';
    }
}