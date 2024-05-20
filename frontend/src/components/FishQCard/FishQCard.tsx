import { useEffect, useState } from 'react';
import './FishQCard.scss';
interface Props {
  fishQ: string[];
  flip: boolean;
}

export const FishQCard: React.FC<Props> = ({ fishQ, flip }) => {
  const [isFlipped, setIsFlipped] = useState(false);
  console.log(isFlipped);


  useEffect(() => {
    const word = document.querySelector('.fishQ-card-word');
    const translation = document.querySelector('.fishQ-card-translation');
    if (isFlipped) {
      word?.setAttribute('style', 'display: none');
      translation?.setAttribute('style', 'display: flex');
    } else {
      word?.setAttribute('style', 'display: flexs');
      translation?.setAttribute('style', 'display: none');
    }
  }, [isFlipped]);

  return (
    <div className="fishQ-card" onClick={() => setIsFlipped(!isFlipped)}>
      <div className="fishQ-card-content">
        <div className="fishQ-card-word">
          <p>{fishQ[0]}</p>
        </div>
        <div className="fishQ-card-translation" style={{ display: 'none' }}>
          <p>{fishQ[1]}</p>
        </div>
      </div>
    </div>
  );
};
