package by.petko.persistence;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class Theme implements Serializable {
    private Integer themeId;
    private String themeName;
    private Set<ThemeOption> options;
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
    public Set<ThemeOption> getOptions() {
        return options;
    }

    public void setOptions(Set<ThemeOption> options) {
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

    @Transient
    public boolean isStarted() {
        return this.getEndDate() == null &&
                this.getStartDate() != null &&
                this.getStartDate().before(new Date());
    }

    @Transient
    public boolean canBeStarted() {
        return this.getStartDate() == null &&
                this.getEndDate() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Theme)) return false;

        Theme theme = (Theme) o;
        return getThemeName() != null ? getThemeName().equals(theme.getThemeName()) : theme.getThemeName() == null;
    }

    @Override
    public int hashCode() {
        return (getThemeName() != null ? getThemeName().hashCode() : 0);
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
