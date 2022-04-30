import React from 'react';
import cancel from '../../../image/cancel.svg';
import ModalContent from './ModalContent';

const SettingModal = ({
  modalState,
  setModal,
}: {
  modalState: boolean;
  setModal: any;
}) => {
  if (modalState) {
    return (
      <>
        <div className="MyPage__modal--setting">
          <img
            src={cancel}
            className="MyPage__button--cancel"
            onClick={setModal}
          ></img>
          <ModalContent></ModalContent>
        </div>
        <div className="blur"></div>
      </>
    );
  } else {
    return null;
  }
};

export default SettingModal;
