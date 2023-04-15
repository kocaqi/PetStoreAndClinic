export function MenuItem(props) {

    return (
          <div>
              <a href={props.url}>{props.name}</a>
          </div>
    );
}