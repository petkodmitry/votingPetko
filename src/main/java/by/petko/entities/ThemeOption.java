package by.petko.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class ThemeOption implements Serializable {
    private Integer optionId;
    private String optionName;
    private Integer quantity;
    private Theme theme;

    @Id
    @Column(name = "option_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    @Column(name = "option_name", nullable = false, length = 30)
    @NotEmpty
    @Size(max = 30)
    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    @Column(name = "quantity", nullable = false)
    @NotNull
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    @JoinColumn(name = "theme_id")
    @JsonBackReference
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThemeOption)) return false;

        ThemeOption that = (ThemeOption) o;

        if (getOptionId() != null ? !getOptionId().equals(that.getOptionId()) : that.getOptionId() != null)
            return false;
        if (getOptionName() != null ? !getOptionName().equals(that.getOptionName()) : that.getOptionName() != null) return false;
        return  (getQuantity() != null ? getQuantity().equals(that.getQuantity()) : that.getQuantity() == null);
//            return false;
//        return getThemeName() != null ? getThemeName().equals(that.getThemeName()) : that.getThemeName() == null;

    }

    @Override
    public int hashCode() {
        int result = getOptionId() != null ? getOptionId().hashCode() : 0;
        result = 31 * result + (getOptionName() != null ? getOptionName().hashCode() : 0);
        result = 31 * result + (getQuantity() != null ? getQuantity().hashCode() : 0);
        result = 31 * result + (getTheme() != null ? getTheme().getThemeId() != null ? getTheme().getThemeId() : 0 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ThemeOption{" +
                "optionId=" + optionId +
                ", optionName='" + optionName + '\'' +
                ", quantity=" + quantity +
                ", themeId=" + theme.getThemeId() +
                '}';
    }
}
