import ActivityForm from "./activity-form";

const ActivityManager: React.FC = () => {
  return (
    <div>
      <h2>TODO: Activity Manager</h2>
      <ActivityForm onSubmit={(activity) => console.log(activity)} 
       />
      <h2>TODO: Activity Table</h2>
    </div>
  )
};

export default ActivityManager;