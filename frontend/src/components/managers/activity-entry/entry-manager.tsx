import { fetchAllActivities } from "../../../services/activity-service";
import SelectorCell from "../cells/selector";
import { ActivityEntry, ColumnMetadata } from "../../../types/entities";
import { Button } from "@mui/material";
import ManagerBase from "../manager-base";
import ActivityEntryForm from "./entry-form";
import { createActivityEntry, deleteActivityEntry, fetchActivityEntries, updateActivityEntry } from "../../../services/activity-entry-service";
import DatePickerCell from "../cells/datetime-picker";
import dayjs from "../../../utils/dayjs-setup";




const ActivityEntryManager: React.FC = () => {
  const columns: Array<ColumnMetadata<ActivityEntry, any>> = [
    {
      key: 'activity',
      header: 'Activity',
      sortable: true,
      editable: true,
      renderEditCell: (value, onChange) => (
        <SelectorCell
          value={value}
          onChange={onChange}
          fetchItems={fetchAllActivities}
          label="Activity"
          itemKey="name"
          itemValue="name"
        />
      )
    },
    {
      key: 'startTime',
      header: 'Start Time',
      sortable: true,
      editable: true,
      renderCell: (value : any) => {
        return dayjs(value).format('LLL');
      },
      renderEditCell: (value, onChange) => (
        <DatePickerCell
          value={dayjs(value)}
          onChange={onChange}
          label="Start Time"
          />
      )
    },
    {
      key: 'endTime',
      header: 'End Time',
      sortable: true,
      editable: true,
      renderCell: (value : any) => {
        var locale = dayjs.tz.guess();
        console.log(JSON.stringify(locale));
        const converted = dayjs(value).tz(locale).format('LLL');
        console.log(JSON.stringify(converted));
        return dayjs(value).format('LLL');
      },
      renderEditCell: (value, onChange) => (
        <DatePickerCell
          value={dayjs(value)}
          onChange={onChange}
          label="Start Time"
          />
      )
    }

  ]

  return (
    <ManagerBase
      managedItemName="Activity Entry"
      columns={columns}
      fetchItems={fetchActivityEntries}
      createItem={createActivityEntry}
      deleteItem={deleteActivityEntry}
      editItem={updateActivityEntry}
      FormComponent={ActivityEntryForm}
      customFilter={(value, setFilter) => (
        <div className="flex items-center space-x-2">
          <SelectorCell
            value={value}
            onChange={setFilter}
            fetchItems={fetchAllActivities}
            label="Filter by activity"
            itemKey="name"
            itemValue="name"
            formControlProps={{ fullWidth: true, margin: "normal", }} />
          <Button variant="contained" color="error" onClick={() => setFilter('')} disabled={value === ''}>
            X
          </Button>
        </div>
      )}
    />
  )
}
export default ActivityEntryManager;
