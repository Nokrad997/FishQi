import "./AdminModal.scss"
interface Props {
    isOpen: boolean;
    onClose: () => void;
}

const AdminModal: React.FC<Props> = ({isOpen, onClose}) => {
  if(!isOpen) {
    return null;
  }
    return (
    <div className="admin-modal">
      <div className="admin-card">
        <img src="src/assets/icons/reject.png" alt="reject" className="reject-icon" width={40} onClick={onClose} />
      </div>
    </div>
  );
};

export default AdminModal;
