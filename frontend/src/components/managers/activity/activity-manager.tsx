import { useState } from "react";
import { createActivity, deleteActivity, editActivity, fetchActivities } from "../../../services/activity-service";
import SelectorCell from "../cells/selector";
import { fetchCategories } from "../../../services/category-service";
import { Activity, ColumnMetadata } from "../../../types/entities";
import { Button } from "@mui/material";
import ActivityForm from "./activity-form";
import ManagerBase from "../manager-base";
import MultilineEditDialog from "../cells/multiline";
import { mapFieldToId } from "../../../utils/mapFieldToId";

const ActivityManager: React.FC = () => {
  const [editOpen, setEditOpen] = useState<boolean>(false);
  const [currentDescription, setCurrentDescription] = useState<string>("");
  const [editCallback, setEditCallback] = useState<(value: string) => void>(() => {});
  const handleOpenEdit = (value : string, onChange: (value : string) => void) => {
    setEditCallback(() => onChange)
    setCurrentDescription(value);
    setEditOpen(true);
  }
  const columns: Array<ColumnMetadata<Activity, any>> = [
    {
      key: 'category',
      header: 'Category',
      sortable: true,
      editable: true,
      renderEditCell: (value, onChange) => (
        <SelectorCell
          value={value}
          onChange={onChange}
          fetchItems={fetchCategories}
          label="Category"
          itemKey="name"
          itemValue="name"/>
      )
    },
    {
      key: 'name',
      header: 'Name',
      sortable: true,
      editable: true
    },
    {
      key: 'description',
      header: 'Description',
      sortable: false,
      editable: true,
      renderEditCell: (value, onChange) => (
        <Button variant="outlined" onClick={() => handleOpenEdit(value, onChange)}>
          Edit
        </Button>
      )
    }
  ];

  return (
    <>
      <ManagerBase
        managedItemName="Activity"
        columns={columns}
        fetchItems={fetchActivities}
        createItem={createActivity}
        deleteItem={deleteActivity}
        editItem={editActivity}
        FormComponent={ActivityForm}
        itemTransform={(item) => mapFieldToId('name', item)}/>
      <MultilineEditDialog
        open={editOpen}
        initialValue={currentDescription}
        onClose={() => {setEditOpen(false); setEditCallback(() => {}); setCurrentDescription("");}}
        onSave={editCallback}
        label="Description"
        />
    </>
  )
};
export default ActivityManager;