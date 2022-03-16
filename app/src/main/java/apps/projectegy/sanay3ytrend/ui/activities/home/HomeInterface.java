package apps.projectegy.sanay3ytrend.ui.activities.home;


import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.workersAds.GetWorkersAdsResponse;

public interface HomeInterface {
    void GetAllCountries(GetAllCountriesResponse getAllCountriesResponse);

    void GetDepartmentsResponse(GetDepartmentsResponse getDepartmentsResponse);

    void GetWorkersAdsResponse(GetWorkersAdsResponse getWorkersAdsResponse);
}
