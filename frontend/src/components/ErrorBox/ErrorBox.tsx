import "./ErrorBox.scss";

const ErrorBox = ({ error }: { error: string }) => {
  return (
    <div className="error-box" style={{display: "none"}}>
      <h3>Error</h3>
      <p>{error}</p>
    </div>
  );
};

export default ErrorBox;
