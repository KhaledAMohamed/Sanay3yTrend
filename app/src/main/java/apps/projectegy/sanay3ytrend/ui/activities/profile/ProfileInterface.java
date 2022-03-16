package apps.projectegy.sanay3ytrend.ui.activities.profile;


import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.profile.GetProfileResponse;

public interface ProfileInterface {
    void GetProfileResponse(GetProfileResponse response);

    void GetDepartmentsResponse(GetDepartmentsResponse getDepartmentsResponse);

}
