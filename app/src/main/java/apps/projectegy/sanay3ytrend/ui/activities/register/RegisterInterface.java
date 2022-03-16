package apps.projectegy.sanay3ytrend.ui.activities.register;


import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;

public interface RegisterInterface {
    void GetAllCountries(GetAllCountriesResponse getAllCountriesResponse);

    void GetDepartmentsResponse(GetDepartmentsResponse getDepartmentsResponse);
}
