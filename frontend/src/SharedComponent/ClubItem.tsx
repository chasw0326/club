import { clubItem } from '../type/type';
import { useNavigate } from 'react-router-dom';
import './Style/shared.scss';
import thumbnail from '../image/thumbnail.svg';

const ClubItem = ({ item }: { item: clubItem }) => {
  const navigate = useNavigate();

  const sectionStyle = {
    backgroundImage: `url("${item?.imageUrl ? item?.imageUrl : thumbnail}")`,
    backgroundSize: '600px 250px',
  };

  return (
    <div
      className="MainBody-itemFrame-clubItem"
      style={sectionStyle}
      onClick={() => {
        navigate(`/information/${item?.id}`);
      }}
    >
      <div className="MainBody-itemFrame-blackCover">
        {' '}
        <div className='"MainBody-itemFrame-clubItem-right'>
          <div className="MainBody-itemFrame-clubItem-title">{item?.name}</div>
          <div className="MainBody-itemFrame-clubItem-description">
            {item?.description}
          </div>
          <div className="MainBody-itemFrame-clubItem-member">
            인원 {item?.clubMembers}명
          </div>
          <div className="MainBody-itemFrame-clubItem-address">
            위치 {item?.address}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClubItem;
