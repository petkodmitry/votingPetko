package by.petko.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Theme implements Serializable {
    private Integer themeId;
    private String themeName;
    private List<ThemeOption> options;
    private Date startDate;
    private Date endDate;
    private String link;

    @Id
    @Column(name = "theme_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    @Column(name = "theme_name", nullable = false, length = 50, unique = true)
    @NotEmpty
    @Size(max = 50)
    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @OneToMany(mappedBy = "theme", cascade = javax.persistence.CascadeType.ALL)
    @JsonManagedReference
    public List<ThemeOption> getOptions() {
        return options;
    }

    public void setOptions(List<ThemeOption> options) {
        this.options = options;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "link", length = 100)
    @Size(max = 100)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Theme)) return false;

        Theme theme = (Theme) o;

        if (getThemeId() != null ? !getThemeId().equals(theme.getThemeId()) : theme.getThemeId() != null)
            return false;
        if (getThemeName() != null ? !getThemeName().equals(theme.getThemeName()) : theme.getThemeName() != null) return false;
        if (getOptions() != null ? !getOptions().equals(theme.getOptions()) : theme.getOptions() != null)
            return false;
        if (getStartDate() != null ? !getStartDate().equals(theme.getStartDate()) : theme.getStartDate() != null)
            return false;
        if (getLink() != null ? !getLink().equals(theme.getLink()) : theme.getLink() != null) return false;
        return getEndDate() != null ? getEndDate().equals(theme.getEndDate()) : theme.getEndDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getThemeId() != null ? getThemeId().hashCode() : 0;
        result = 31 * result + (getThemeName() != null ? getThemeName().hashCode() : 0);
        result = 31 * result + (getOptions() != null ? getOptions().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        result = 31 * result + (getLink() != null ? getLink().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String optionsToString = "{";
        for (ThemeOption themeOption : options) {
            optionsToString += themeOption.toString();
            optionsToString += "; ";
        }
        optionsToString += "}";
        return "Theme{" +
                "themeName='" + themeName + '\'' +
                ", options=" + optionsToString +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", themeId=" + themeId +
                ", link=" + link +
                '}';
    }
}
